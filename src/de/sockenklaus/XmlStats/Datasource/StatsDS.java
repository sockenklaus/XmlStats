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
import com.nidefawl.Stats.datasource.PlayerStat;
import com.nidefawl.Stats.datasource.PlayerStatSQL;

import de.sockenklaus.XmlStats.XmlStats;

// TODO: Auto-generated Javadoc
/**
 * The Class StatsDS.
 */
public class StatsDS extends Datasource {
	
	/** The stats plugin. */
	private Stats statsPlugin;
	//private Server serverRef;
	/** The all player names. */
	private ArrayList<String> allPlayerNames;
	
	/** The stats. */
	private HashMap<String, PlayerStat> stats = new HashMap<String, PlayerStat>();

	/**
	 * Instantiates a new stats ds.
	 */
	public StatsDS() {
		this.statsPlugin = XmlStats.getStatsPlugin();
		//this.serverRef = XmlStats.getServerRef();
		this.allPlayerNames = fetchAllPlayers();
		this.stats = fetchAllPlayerStats(allPlayerNames);
	}
	
	/**
	 * Gets the plugin.
	 *
	 * @return the plugin
	 */
	public Stats getPlugin() {
		return this.statsPlugin;
	}
	
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
	private HashMap<String, PlayerStat> fetchAllPlayerStats(ArrayList<String> pPlayerNames){
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
}
