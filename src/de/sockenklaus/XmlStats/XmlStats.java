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
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.iConomy.iConomy;
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
	
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.Plugin#onDisable()
	 */
	@Override
	public void onDisable() {
		Webserver webserverTemp = (Webserver)XmlStatsRegistry.get("webserver");
		
		if(this.enabled && webserverTemp.isRunning()){
			this.enabled = false;
				
			webserverTemp.stopServer();
			
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
				
		XmlStatsRegistry.put("settings", new Settings(this));
		XmlStatsRegistry.put("xmlstats", this);
		
		Settings settingsTemp = (Settings)XmlStatsRegistry.get("settings");
		
		LogDebug("Settings read:");
		LogDebug("options.webserver-enabled: "+settingsTemp.getBoolean("options.webserver-enabled"));
		LogDebug("options.webserver-port: "+settingsTemp.getInt("options.webserver-port"));
		LogDebug("options.gzip-enabled: "+settingsTemp.getBoolean("options.gzip-enabled"));
		LogDebug("options.verbose-enabled: "+settingsTemp.getBoolean("options.verbose-enabled"));
		
		this.hookPlugins();

		if (settingsTemp.getBoolean("options.webserver-enabled")){
			try {
				XmlStatsRegistry.put("webserver", new Webserver(settingsTemp.getInt("options.webserver-port")));
					
				this.enabled = true;
				LogInfo("Plugin Enabled");
			}
			catch (Exception ex){
				LogError("Fehler beim Erstellen des Webservers:");
				LogError(ex.getMessage());
				ex.printStackTrace();
			}			
		}
		else {
			LogWarn("Webserver ist derzeit in der "+settingsTemp.getSettingsFilename()+" deaktiviert.");
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
	
	public static void LogDebug(String Message){
		Settings settingsTemp = (Settings)XmlStatsRegistry.get("settings");
		if(settingsTemp.getBoolean("options.verbose-enabled")){
			log.log(Level.INFO, logprefix+"[DEBUG] "+Message);
		}
	}
	
	protected void hookPlugins(){
		Plugin StatsTemp = getServer().getPluginManager().getPlugin("Stats");
		Plugin iConomyTemp = getServer().getPluginManager().getPlugin("iConomy");

        if(StatsTemp != null){
        	if(StatsTemp.isEnabled() && StatsTemp.getClass().getName().equals("com.nidefawl.Stats.Stats")){
        		XmlStatsRegistry.put("stats", (Stats)StatsTemp);
        		LogInfo("Hooked into Stats!");
        	}
        }
        else {
        	LogError("Stats not found! Can't hook into it.");
        }
        if (iConomyTemp != null) {
            if (iConomyTemp.isEnabled() && iConomyTemp.getClass().getName().equals("com.iConomy.iConomy")) {
                XmlStatsRegistry.put("iconomy", (iConomy)iConomyTemp);
                LogInfo("Hooked into iConomy");
            }
        }
        else {
        	LogError("iConomy not found! Can't hook into it.");
        }
	}	
	public static boolean isStatsHooked(){
		Stats StatsTemp = (Stats)XmlStatsRegistry.get("stats");
		
		if (StatsTemp != null){
			if(StatsTemp.getClass().getName().equals("com.nidefawl.Stats.Stats") && StatsTemp.isEnabled()) return true;
		}
		return false;
	}
	
	public static boolean isiConomyHooked(){
		iConomy iConomyTemp = (iConomy)XmlStatsRegistry.get("iconomy");
		
		if (iConomyTemp != null){
			if (iConomyTemp.getClass().getName().equals("com.iConomy.iConomy") && iConomyTemp.isEnabled()) return true;
		}
		return false;
	}
}
