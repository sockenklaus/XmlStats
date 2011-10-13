/*
 * Copyright (C) [2011]  [Pascal König]
*
* This program is free software; you can redistribute it and/or modify it under the terms of
* the GNU General Public License as published by the Free Software Foundation; either version
* 3 of the License, or (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License along with this program; 
* if not, see <http://www.gnu.org/licenses/>. 
*/
package de.sockenklaus.XmlStats.Datasource;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.bukkit.plugin.Plugin;

import com.nidefawl.Stats.Stats;
import com.nidefawl.Stats.datasource.Category;
import com.nidefawl.Stats.datasource.PlayerStat;
import com.nidefawl.Stats.datasource.PlayerStatSQL;

import de.sockenklaus.XmlStats.Util;
import de.sockenklaus.XmlStats.Webserver;
import de.sockenklaus.XmlStats.XmlStats;
//import de.sockenklaus.XmlStats.XmlStatsRegistry;

// TODO: Auto-generated Javadoc
/**
 * The Class StatsDS.
 */
public class StatsDS extends Datasource {
	
	private static StatsDS instance;
	private Plugin stats;
	
	
	private StatsDS(){
		super();
	}
	
	public static StatsDS getInstance(){
		XmlStats.LogDebug("Let's get an instance of StatsDS");
		if(instance == null){
			XmlStats.LogDebug("There's no instance of StatsDS.");
			if(Util.checkStats()){
				XmlStats.LogDebug("Looks like Stats is up and running.");
				instance = new StatsDS();
				instance.hookStats();
			}
			else {
				XmlStats.LogWarn("Stats not found! Can't hook into it.");
			}
		}
		return instance;
	}
	/**
	 * Gets the data folder.
	 *
	 * @return the data folder
	 */
	public File getDataFolder(){		
		return this.stats.getDataFolder();
	}
	
	public HashMap<String, Category> getAddedStats(List<String> playerList){
		HashMap <String, Category> result = new HashMap<String, Category>();
				
		for(String playerName : playerList){
			PlayerStat player = getPlayerStat(playerName);
			
			for(String catName : player.getCats()){
				Category cat = player.get(catName);
				
				for(String entryName : cat.getEntries()){
					Integer entry = cat.get(entryName);
					
					if(result.containsKey(catName)){
						
						if(entryName.equals("lastlogin") || entryName.equals("lastlogout")){
							result.get(catName).put(entryName, Math.max(result.get(catName).get(entryName), entry));
						}
						else {
							result.get(catName).add(entryName, entry);
						}
						
					}
					else {
						Category tempCat = new Category();
						tempCat.add(entryName, entry);
						result.put(catName, tempCat);
					}
				}
			}
		}
		
		return result;
	}

	public PlayerStat getPlayerStat(String playerName){
			
		PlayerStat result = new PlayerStatSQL(playerName, this.getStats());
		
		result.load();
		
		return result;
	}
	
	private void hookStats(){
		Plugin StatsTemp = xmlstats.getServer().getPluginManager().getPlugin("Stats");
		Webserver webserver = Webserver.getInstance();
		
        this.stats = StatsTemp;
        XmlStats.LogInfo("Hooked into Stats!");
        webserver.startStats();
	}
	

	
	public Stats getStats(){
		return (Stats)this.stats;
	}
}
