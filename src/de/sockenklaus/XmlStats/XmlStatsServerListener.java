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
		iConomy iConomy = (iConomy)XmlStatsRegistry.get("iconomy");
		Stats Stats = (Stats)XmlStatsRegistry.get("stats");
		
		if(iConomy != null){
			if(event.getPlugin().getDescription().getName().equals("iConomy")){
				iConomy = null;
				XmlStats.LogInfo("iConomy is disabled now. Unhooking.");
			}
		}
		if(Stats != null){
			if(event.getPlugin().getDescription().getName().equals("Stats")){
				Stats = null;
				XmlStats.LogInfo("Stats is disabled now. Unhooking.");
			}
		}
		
	}
	
	public void onPluginEnable(PluginEnableEvent event){
		plugin.hookPlugins();
	}
}
