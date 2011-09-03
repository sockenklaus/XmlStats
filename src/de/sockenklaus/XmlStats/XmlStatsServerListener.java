/**
 * 
 */
package de.sockenklaus.XmlStats;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import com.iConomy.iConomy;
import com.nidefawl.Stats.Stats;

/**
 * @author socrates
 *
 */
public class XmlStatsServerListener extends ServerListener {
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
	
	public void onPluginEnable(PluginEnableEvent event){
		if(this.identifyPlugin(event, "stats")){
			this.plugin.hookStats();
		}
		if(this.identifyPlugin(event, "achievements")){
			this.plugin.hookAchievements();
		}
		if(this.identifyPlugin(event, "iconomy")){
			this.plugin.hookiConomy();
		}
	}
	
	private boolean identifyPlugin(PluginEnableEvent event, String name){
		return event.getPlugin().getDescription().getName().equalsIgnoreCase(name);
	}
}
