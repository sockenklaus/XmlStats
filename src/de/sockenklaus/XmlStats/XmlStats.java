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
	private static Stats Stats = null;
	private static iConomy iConomy = null;
	private static Server serverRef;
	private WebServer webServer;
	private Settings settings;
	
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.Plugin#onDisable()
	 */
	@Override
	public void onDisable() {
		if(this.enabled && this.webServer.isRunning()){
			this.enabled = false;
			
			iConomy = null;
			Stats = null;
			
			this.webServer.stopServer();
			
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
		
		serverRef = getServer();
		this.settings = new Settings(this);
		
		this.hookPlugins();

		if (this.settings.getBoolean("options.webserver-enabled")){
			try {
				this.webServer = new WebServer(settings.getInt("options.webserver-port"));
					
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
		return Stats;
	}
	
	/**
	 * Gets the server ref.
	 *
	 * @return the server ref
	 */
	public static Server getServerRef(){
		return serverRef;
	}
	
	public static iConomy getiConomyPlugin(){
		return iConomy;
	}
	
	public void onPluginDisable(PluginDisableEvent event){
		if(iConomy != null){
			if(event.getPlugin().getDescription().getName().equals("iConomy")){
				iConomy = null;
				LogInfo("iConomy is disabled now. Unhooking.");
			}
		}
		if(Stats != null){
			if(event.getPlugin().getDescription().getName().equals("Stats")){
				Stats = null;
				LogInfo("Stats is disabled now. Unhooking.");
			}
		}
		
	}
	
	public void onPluginEnable(PluginEnableEvent event){
		this.hookPlugins();
	}
	
	private void hookPlugins(){
		Plugin StatsTemp = getServer().getPluginManager().getPlugin("Stats");
		Plugin iConomyTemp = getServer().getPluginManager().getPlugin("iConomy");

        if(StatsTemp != null){
        	if(StatsTemp.isEnabled() && StatsTemp.getClass().getName().equals("com.nidefawl.Stats.Stats")){
        		Stats = (Stats)StatsTemp;
        		LogInfo("Hooked into Stats!");
        	}
        }
        else {
        	LogError("Stats not found! Can't hook into it.");
        }
        if (iConomyTemp != null) {
            if (iConomyTemp.isEnabled() && iConomyTemp.getClass().getName().equals("com.iConomy.iConomy")) {
                iConomy = (iConomy)iConomyTemp;
                LogInfo("Hooked into iConomy");
            }
        }
        else {
        	LogError("iConomy not found! Can't hook into it.");
        }
	}
	
	public static boolean isStatsHooked(){
		if (Stats != null){
			if(Stats.getClass().getName().equals("com.nidefawl.Stats.Stats") && Stats.isEnabled()) return true;
		}
		return false;
	}
	
	public static boolean isiConomyHooked(){
		if (iConomy != null){
			if (iConomy.getClass().getName().equals("com.iConomy.iConomy") && iConomy.isEnabled()) return true;
		}
		return false;
	}
}
