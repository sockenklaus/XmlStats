/*
 * Copyright (C) [2011]  [Pascal Koenig]
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

import terranetworkorg.Stats.Stats;
import terranetworkorg.Stats.Storage.PlayerCache;
import terranetworkorg.Stats.Storage.PlayerControl;
import terranetworkorg.Stats.Storage.PlayerStats;

/*import com.nidefawl.Stats.Stats;
import com.nidefawl.Stats.datasource.Category;
import com.nidefawl.Stats.datasource.PlayerStat;
import com.nidefawl.Stats.datasource.PlayerStatSQL;*/

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
	
	public static HashMap<String, HashMap<String, Integer>> getAddedStats(List<String> playerList){
		HashMap <String, HashMap<String, Integer>> result = new HashMap<String, HashMap<String, Integer>>();
		
		for(String playerName : playerList){
			PlayerCache player = getPlayerCache(playerName);
			
			for(PlayerStats ps : player.getStats()){
				if(!result.containsKey(ps.getCat())){
					/*
					 * Fall 1:
					 * Result enthaelt schon die Kat nicht
					 */
					HashMap<String, Integer> tmpMap = new HashMap<String, Integer>();
					tmpMap.put(ps.getName(), ps.getValue());
					result.put(ps.getCat(), tmpMap);
				}
				else if(!result.get(ps.getCat()).containsKey(ps.getName())){
					/*
					 * Fall 2: Result enthaelt Cat aber Stat nicht.
					 */
					result.get(ps.getCat()).put(ps.getName(), ps.getValue());
				}
				else {
					/*
					 * Fall 3: Es ist beides schon vorhanden
					 */
					Integer currVal = result.get(ps.getCat()).get(ps.getName());
					
					if(ps.getName().equals("lastlogin") || ps.getName().equals("lastlogout")){
						result.get(ps.getCat()).put(ps.getName(), Math.max(ps.getValue(), currVal));
					}
					else {
						result.get(ps.getCat()).put(ps.getName(), currVal + ps.getValue());
					}
				}
			}
		}
				
		return result;
	}

	public static HashMap<String, HashMap<String, Integer>> getStats(String playerName){
		HashMap<String, HashMap<String, Integer>> result = new HashMap<String, HashMap<String, Integer>>();
		
		PlayerCache player = getPlayerCache(playerName);
		
		for(PlayerStats ps : player.getStats()){
			if(!result.containsKey(ps.getCat())){
				/*
				 * Fall 1: Result enthaelt die Kategorie nicht.
				 */
				HashMap<String, Integer> tmpMap = new HashMap<String, Integer>();
				tmpMap.put(ps.getName(), ps.getValue());
				result.put(ps.getCat(), tmpMap);
			}
			else if(!result.get(ps.getCat()).containsKey(ps.getName())){
				/*
				 * Fall 2: Result kennt Kategorie, aber nicht den Stat.
				 */
				result.get(ps.getCat()).put(ps.getName(), ps.getValue());
			}
			else {
				/*
				 * Fall 3 (sollte nicht auftreten): Result kennt beides schon.
				 * Im Zweifel ueberschreiben...
				 */
				result.get(ps.getCat()).put(ps.getName(), ps.getValue());
			}
		}
		
		return result;
		
	}

	private static PlayerCache getPlayerCache(String playerName){
		return PlayerControl.getPlayerCache(playerName);
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
