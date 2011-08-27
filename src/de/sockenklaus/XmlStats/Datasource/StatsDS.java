package de.sockenklaus.XmlStats.Datasource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.nidefawl.Stats.Stats;
import com.nidefawl.Stats.datasource.PlayerStat;
import com.nidefawl.Stats.datasource.PlayerStatSQL;

import de.sockenklaus.XmlStats.XmlStats;

public class StatsDS extends Datasource {
	private Stats statsPlugin;
	//private Server serverRef;
	private ArrayList<String> allPlayerNames;
	private HashMap<String, PlayerStat> stats = new HashMap<String, PlayerStat>();

	public StatsDS() {
		this.statsPlugin = XmlStats.getStatsPlugin();
		//this.serverRef = XmlStats.getServerRef();
		this.allPlayerNames = fetchAllPlayers();
		this.stats = fetchAllPlayerStats(allPlayerNames);
	}
	
	public Stats getPlugin() {
		return this.statsPlugin;
	}
	
	public File getDataFolder(){
		return this.statsPlugin.getDataFolder();
	}
	
	private HashMap<String, PlayerStat> fetchAllPlayerStats(ArrayList<String> pPlayerNames){
		HashMap<String, PlayerStat> result = new HashMap<String, PlayerStat>();
		
		for (String playerName : pPlayerNames){
						
			PlayerStat ps = new PlayerStatSQL(playerName, statsPlugin);
			ps.load();
			result.put(playerName, ps);
		}
		
		return result;
	}
	
	public HashMap<String, PlayerStat> getStats(){
		return this.stats;
	}
}
