/**
 * 
 */
package de.sockenklaus.XmlStats.Datasource;

import java.io.File;
import java.util.HashMap;

import com.nidefawl.Achievements.AchievementListData;
import com.nidefawl.Achievements.Achievements;
import com.nidefawl.Achievements.PlayerAchievement;
import com.nidefawl.Achievements.PlayerAchievementFile;
import com.nidefawl.Achievements.PlayerAchievementSQL;

import de.sockenklaus.XmlStats.XmlStats;
import de.sockenklaus.XmlStats.XmlStatsRegistry;

/**
 * @author socrates
 *
 */
public class AchievementsDS extends Datasource {
	private XmlStats xmlstats = null;
	
	public AchievementsDS(){
		this.xmlstats = (XmlStats)XmlStatsRegistry.get("xmlstats");
	}
	//HashMap<String, PlayerAchievement> playerAchievementsList;
	
	public HashMap<String, AchievementListData> getAchievementsList(){
		Achievements ach = (Achievements)XmlStatsRegistry.get("achievements");
		
		if(xmlstats.checkAchievements()){
			XmlStats.LogDebug("Found "+ach.achievementList.size()+" achievements.");
			return ach.achievementList;
		}
		else return new HashMap<String, AchievementListData>();
	}
	
	/*public void refreshPlayerAchievements(){
		PlayerAchievement pa;
		Achievements ach = (Achievements)XmlStatsRegistry.get("achievements");
		
		if(ach.useSQL){
			
		}
	}*/
	
	public PlayerAchievement getUserAchievement(String playerName){
		
		PlayerAchievement pa;
		Achievements ach = (Achievements)XmlStatsRegistry.get("achievements");
				
		if(ach.useSQL){
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
}
