/**
 * 
 */
package de.sockenklaus.XmlStats.Datasource;

import java.io.File;
import java.util.HashMap;

import org.bukkit.plugin.Plugin;

import com.nidefawl.Achievements.AchievementListData;
import com.nidefawl.Achievements.Achievements;
import com.nidefawl.Achievements.PlayerAchievement;
import com.nidefawl.Achievements.PlayerAchievementFile;
import com.nidefawl.Achievements.PlayerAchievementSQL;

import de.sockenklaus.XmlStats.Util;
import de.sockenklaus.XmlStats.Webserver;
import de.sockenklaus.XmlStats.XmlStats;

/**
 * @author socrates
 *
 */
public class AchievementsDS extends Datasource {

	private Plugin ach; // = (Achievements)XmlStatsRegistry.get("achievements");
	private static AchievementsDS instance;
	
	private AchievementsDS(){
		super();
	}
	
	private void hookAchievements(){
		Plugin AchievementsTemp = this.xmlstats.getServer().getPluginManager().getPlugin("Achievements");
		Webserver webserver = Webserver.getInstance();
		
		this.ach = AchievementsTemp;
		
        XmlStats.LogInfo("Hooked into Achievements!");
        webserver.startAchievements();
	}
	
	public static AchievementsDS getInstance(){
		if(instance == null){
			XmlStats.LogDebug("There's no instance of AchievementsDS");
			if(Util.checkAchievements()){
				XmlStats.LogDebug("Achievements seems to be there...");
				instance = new AchievementsDS();
				instance.hookAchievements();
			}
			else {
				XmlStats.LogWarn("Achievements not found! Can't hook into it.");
			}
		}
		return instance;
	}
	
	public  HashMap<String, AchievementListData> getAchievementsList(){
		return this.getAchievements().achievementList;
	}
	
	public PlayerAchievement getUserAchievement(String playerName){
		
		PlayerAchievement pa;
					
		if(this.getAchievements().useSQL){
			String location = ach.getDataFolder().getPath() + File.separator + playerName + ".txt";
			File fold = new File(location);
			
			pa = new PlayerAchievementSQL(playerName);
			if(fold.exists()){
				PlayerAchievement paold = new PlayerAchievementFile(ach.getDataFolder().getPath(), playerName);
				paold.load();
				File fnew = new File(location + ".old");
				fold.renameTo(fnew);
				pa.copy(paold);
				pa.save();
			}
		}
		else pa = new PlayerAchievementFile(ach.getDataFolder().getPath(), playerName);
		
		pa.load();
				
		return pa;
	}


	
	public Achievements getAchievements(){
		return (Achievements)this.ach;
	}
}
