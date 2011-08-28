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
import java.util.ArrayList;
import java.util.HashMap;

import com.nidefawl.Stats.Stats;
import com.nidefawl.Stats.datasource.Category;
import com.nidefawl.Stats.datasource.PlayerStat;
import com.nidefawl.Stats.datasource.PlayerStatSQL;

import de.sockenklaus.XmlStats.XmlStats;

// TODO: Auto-generated Javadoc
/**
 * The Class StatsDS.
 */
public class UserstatsDS extends Datasource {
	
	private Stats statsPlugin;
	private ArrayList<String> allPlayerNames;
	private HashMap<String, PlayerStat> stats = new HashMap<String, PlayerStat>();

	/**
	 * Instantiates a new stats ds.
	 */
	public UserstatsDS() {
		this.statsPlugin = XmlStats.getStatsPlugin();
		this.allPlayerNames = fetchAllPlayers();
		this.stats = fetchPlayerStats(allPlayerNames);
	}
	
	/**
	 * Gets the plugin.
	 *
	 * @return the plugin
	 */
//	public Stats getPlugin() {
	//	return this.statsPlugin;
	//}
	
	/**
	 * Gets the data folder.
	 *
	 * @return the data folder
	 */
	public File getDataFolder(){
		return this.statsPlugin.getDataFolder();
	}
	
	/**
	 * Fetch all player stats.
	 *
	 * @param pPlayerNames the player names
	 * @return the hash map
	 */
	private HashMap<String, PlayerStat> fetchPlayerStats(ArrayList<String> pPlayerNames){
		HashMap<String, PlayerStat> result = new HashMap<String, PlayerStat>();
		
		for (String playerName : pPlayerNames){
						
			PlayerStat ps = new PlayerStatSQL(playerName, statsPlugin);
			ps.load();
			result.put(playerName, ps);
		}
		
		return result;
	}
	
	/**
	 * Gets the stats.
	 *
	 * @return the stats
	 */
	public HashMap<String, PlayerStat> getStats(){
		return this.stats;
	}
	
	public HashMap<String, HashMap<String, Integer>> getAddedStats(){
		HashMap <String, HashMap<String, Integer>> result = new HashMap<String, HashMap<String, Integer>>();
		
		for(String playerName : this.stats.keySet()){
			PlayerStat player = this.stats.get(playerName);
			
			for(String catName : player.getCats()){
				Category cat = player.get(catName);
				
				for(String entryName : cat.getEntries()){
					Integer entry = cat.get(entryName);
					
					if(result.containsKey(catName)){
						if(result.get(catName).containsKey(entryName)){
							
							if(entryName.equals("lastlogin") || entryName.equals("lastlogout")){
								result.get(catName).put(entryName, Math.max(result.get(catName).get(entryName), entry));
							}
							else {
								Integer tempInt = result.get(catName).get(entryName) + entry;
								result.get(catName).put(entryName, tempInt);
							}

						}
						else {
							result.get(catName).put(entryName, entry);
						}
					}
					else {
						HashMap<String, Integer> tempMap = new HashMap<String, Integer>();
						tempMap.put(entryName, entry);
						result.put(catName, tempMap);
					}
				}
			}
		}
		
		return result;
	}
	
	public void sortStats(){
		
	}
}
