/**
 * 
 */
package de.sockenklaus.XmlStats;

import org.bukkit.plugin.Plugin;

import com.nijikokun.register.payment.Methods;

/**
 * @author socrates
 *
 */
public class Util {
	/**
	 * Checks if is Achievements hooked.
	 *
	 * @return true, if is Achievements hooked
	 */
	public static boolean checkAchievements(){
		Plugin AchievementsTemp = XmlStats.getInstance().getServer().getPluginManager().getPlugin("Achievements");
		
		if(AchievementsTemp != null && AchievementsTemp.getClass().getName().equals("com.nidefawl.Achievements.Achievements") && AchievementsTemp.isEnabled()) return true;
		return false;
	}
	
	public static boolean checkRegister(){
		Plugin registerTemp = XmlStats.getInstance().getServer().getPluginManager().getPlugin("Register");
				
		if (registerTemp != null && registerTemp.getClass().getName().equals("com.nijikokun.register.Register") && registerTemp.isEnabled() && Methods.hasMethod()) return true;
		return false;
	}
	
	/**
	 * Checks if is stats hooked.
	 *
	 * @return true, if is stats hooked
	 */
	public static boolean checkStats(){
		Plugin StatsTemp = XmlStats.getInstance().getServer().getPluginManager().getPlugin("Stats");
		
		if(StatsTemp != null && StatsTemp.getClass().getName().equals("com.nidefawl.Stats.Stats") && StatsTemp.isEnabled()) return true;
		return false;
	}
}
