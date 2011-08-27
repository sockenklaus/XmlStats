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
package de.sockenklaus.XmlStats;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import com.nidefawl.Stats.Stats;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlStats.
 */
public class XmlStats extends JavaPlugin {

	private final static Logger log = Logger.getLogger("Minecraft");
	private final static double version = 0.01;
	private final static String logprefix = "[XmlStats]";
	private boolean enabled = false;
	private static Stats statsPlugin;
	private static Server serverRef;
	private WebServer xmlQueryServer;
	private Settings settings;
	
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.Plugin#onDisable()
	 */
	@Override
	public void onDisable() {
		if(enabled && xmlQueryServer.isRunning()){
			enabled = false;
			
			xmlQueryServer.stopServer();
			
			getServer().getScheduler().cancelTasks(this);
			
		}
		LogInfo("Plugin Disabled");
	}

	/* (non-Javadoc)
	 * @see org.bukkit.plugin.Plugin#onEnable()
	 */
	@Override
	public void onEnable() {
		
		getDataFolder().mkdirs();
		statsPlugin = (Stats)getServer().getPluginManager().getPlugin("Stats");
		serverRef = getServer();
		
		settings = new Settings(this);
		
		if (settings.getBoolean("options.webserver-enabled")){
			if (getServer().getPluginManager().isPluginEnabled("Stats")){
				try {
					xmlQueryServer = new WebServer(settings.getInt("options.webserver-port"));
					
					enabled = true;
					LogInfo("Plugin Enabled");
				}
				catch (Exception ex){
					LogError("Fehler beim Erstellen des Webservers:");
					LogError(ex.getMessage());
				}
				
			}
			else {
				LogError("Stats-Plugin laeuft nicht... Breche ab...");
			}
		}
		else {
			LogWarn("Webserver ist derzeit in der "+settings.getSettingsFilename()+" deaktiviert.");
		}
		
		
	}
	
	/**
	 * Log error.
	 *
	 * @param Message the message
	 */
	public static void LogError(String Message) {
		log.log(Level.SEVERE, logprefix + " " + Message);
	}

	/**
	 * Log info.
	 *
	 * @param Message the message
	 */
	public static void LogInfo(String Message) {
		log.info(logprefix + " " + Message);
	}
	
	/**
	 * Log warn.
	 *
	 * @param Message the message
	 */
	public static void LogWarn(String Message){
		log.log(Level.WARNING, logprefix + " "+ Message);
	}
	
	/**
	 * Gets the stats plugin.
	 *
	 * @return the stats plugin
	 */
	public static Stats getStatsPlugin(){
		return statsPlugin;
	}
	
	/**
	 * Gets the server ref.
	 *
	 * @return the server ref
	 */
	public static Server getServerRef(){
		return serverRef;
	}

}
