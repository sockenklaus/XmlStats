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

import com.nidefawl.Stats.Stats;
import com.nidefawl.Stats.datasource.Category;
import com.nidefawl.Stats.datasource.PlayerStat;
import com.nidefawl.Stats.datasource.PlayerStatSQL;

import de.sockenklaus.XmlStats.XmlStatsRegistry;

// TODO: Auto-generated Javadoc
/**
 * The Class StatsDS.
 */
public class UserstatsDS extends Datasource {
	
	private static Stats statsPlugin;
	
	/**
	 * Gets the data folder.
	 *
	 * @return the data folder
	 */
	public static File getDataFolder(){
		statsPlugin = (Stats)XmlStatsRegistry.get("stats");
		return statsPlugin.getDataFolder();
	}
	
	public static HashMap<String, Category> getAddedStats(List<String> playerList){
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

	public static PlayerStat getPlayerStat(String playerName){
		statsPlugin = (Stats)XmlStatsRegistry.get("stats");
		PlayerStat result = new PlayerStatSQL(playerName, statsPlugin);
		
		result.load();
		
		return result;
	}
}
