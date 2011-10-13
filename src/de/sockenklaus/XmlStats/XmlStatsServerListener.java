/**
 * 
 */
package de.sockenklaus.XmlStats;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

import de.sockenklaus.XmlStats.Datasource.AchievementsDS;
import de.sockenklaus.XmlStats.Datasource.RegisterDS;
import de.sockenklaus.XmlStats.Datasource.StatsDS;

/**
 * @author socrates
 *
 */
public class XmlStatsServerListener extends ServerListener {
	
	public void onPluginEnable(PluginEnableEvent event){
		if(this.identifyPlugin(event, "stats") && Util.checkStats()){
			StatsDS.getInstance();
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
