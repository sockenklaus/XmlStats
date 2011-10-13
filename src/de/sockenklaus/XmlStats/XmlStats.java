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

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

import de.sockenklaus.XmlStats.Datasource.AchievementsDS;
import de.sockenklaus.XmlStats.Datasource.RegisterDS;
import de.sockenklaus.XmlStats.Datasource.StatsDS;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlStats.
 */
public class XmlStats extends JavaPlugin {

	private final static Logger log = Logger.getLogger("Minecraft");
	private String version;
	private final static String logprefix = "[XmlStats]";
	private boolean enabled = false;
	private static XmlStats instance;
	
	public static XmlStats getInstance(){
		if(instance == null){
			instance = new XmlStats();
		}
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.Plugin#onDisable()
	 */
	@Override
	public void onDisable() {
		Webserver webserverTemp = Webserver.getInstance();
		
		if(this.enabled && webserverTemp.isRunning()){
			this.enabled = false;
			
			webserverTemp.stop();
			LogDebug("Webserver stopped.");
			
			getServer().getScheduler().cancelTasks(this);
		}
		LogInfo("XmlStats Disabled");
	}
		
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.Plugin#onEnable()
	 */
	@Override
	public void onEnable() {
		this.version = getDescription().getVersion();
		
		getDataFolder().mkdirs();
				
		instance = this;
		
		Settings settingsTemp = Settings.getInstance();
		
		LogDebug("Settings read:");
		LogDebug("options.webserver-enabled: "+settingsTemp.getBoolean("options.webserver-enabled"));
		LogDebug("options.webserver-port: "+settingsTemp.getInt("options.webserver-port"));
		LogDebug("options.verbose-enabled: "+settingsTemp.getBoolean("options.verbose-enabled"));
			
		if (settingsTemp.getBoolean("options.webserver-enabled")){
			Webserver.getInstance();
					
			this.enabled = true;
			LogInfo("XmStats "+this.version+" enabled");
			this.hookPlugins();
			this.registerEvents();			
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
	
	/**
	 * Log debug.
	 *
	 * @param Message the message
	 */
	public static void LogDebug(String Message){
		Settings settingsTemp = Settings.getInstance();
		if(settingsTemp.getBoolean("options.verbose-enabled")){
			log.log(Level.INFO, logprefix+"[DEBUG] "+Message);
		}
	}
	
	/**
	 * Hook plugins.
	 */
	protected void hookPlugins(){
		if(Util.checkStats()) StatsDS.getInstance();
		if(Util.checkAchievements()) AchievementsDS.getInstance();
		if(Util.checkRegister()) RegisterDS.getInstance();
	}
	
	
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		
		if(commandLabel.equalsIgnoreCase("xmlstats")){
			if(sender.isOp()){
				if (args.length == 1 && args[0].equalsIgnoreCase("reload")){
					sender.sendMessage("Reloading the XmlStats plugin...");
					LogInfo("Reloading the XmlStats plugin...");
					this.reload();
					return true;
				}
			}
			else {
				sender.sendMessage("You don't have the permission to do this!");
			}
		}
		
		return false;
	}

	/**
	 * Reload.
	 */
	protected void reload() {
		this.onDisable();
		this.onEnable();
		
	}
	private void registerEvents(){
		XmlStatsServerListener listener = new XmlStatsServerListener();

		getServer().getPluginManager().registerEvent(Type.PLUGIN_ENABLE, listener, Priority.Monitor, this);
	}
}
