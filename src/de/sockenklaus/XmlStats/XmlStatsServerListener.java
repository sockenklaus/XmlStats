/**
 * 
 */
package de.sockenklaus.XmlStats;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import de.sockenklaus.XmlStats.Datasource.AchievementsDS;
import de.sockenklaus.XmlStats.Datasource.RegisterDS;
import de.sockenklaus.XmlStats.Datasource.StatsDS;

/**
 * @author socrates
 *
 */
public class XmlStatsServerListener implements Listener {
	private XmlStats plugin;
	
	public XmlStatsServerListener(XmlStats plugin){
		this.plugin = plugin;
	}
	
	public void onPluginDisable(PluginDisableEvent event){
		/*
		 * TODO
		 * Not implemented yet!
		 */
		
		/*Plugin iConomy = (Plugin)XmlStatsRegistry.get("iconomy");
		Plugin Stats = (Plugin)XmlStatsRegistry.get("stats");
		Plugin Achievements = (Plugin)XmlStatsRegistry.get("achievements");
		
		if (!XmlStats.checkAchievements()){
			
		}
		if(!XmlStats.checkiConomy()){
			
		}
		if(!XmlStats.checkStats()){
			
		}*/
		
	}
	
	@EventHandler
	public void onPluginEnable(PluginEnableEvent event){

		XmlStats.LogDebug("onPluginEnable fired");
		
		if(this.identifyPlugin(event, "stats 2.0")){
			XmlStats.LogDebug("Stats 2.0 fired the event");
			this.plugin.hookStats();
		}
		if(this.identifyPlugin(event, "achievements") && Util.checkAchievements()){
			AchievementsDS.getInstance();
		}
		if(this.identifyPlugin(event, "register") && Util.checkRegister()){
			RegisterDS.getInstance();
		}
	}
	
	private boolean identifyPlugin(PluginEnableEvent event, String name){
		XmlStats.LogDebug(event.getPlugin().getDescription().getName()+" fired an event!");
		return event.getPlugin().getDescription().getName().equalsIgnoreCase(name);
	}
}
