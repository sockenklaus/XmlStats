/**
 * 
 */
package de.sockenklaus.XmlStats.Datasource;

import java.io.File;
import java.util.HashMap;

import com.nidefawl.Achievements.AchPropertiesFile;
import com.nidefawl.Achievements.AchievementListData;
import com.nidefawl.Achievements.Achievements;
import com.nidefawl.Achievements.AchievementsLoader;

import de.sockenklaus.XmlStats.XmlStats;
import de.sockenklaus.XmlStats.XmlStatsRegistry;

/**
 * @author socrates
 *
 */
public class AchievementsDS extends Datasource {

	
	public HashMap<String, AchievementListData> getAchievementsList(){
		Achievements ach = (Achievements)XmlStatsRegistry.get("achievements");
		
		if(XmlStats.checkAchievements()){
			return ach.achievementList;
		}
		else return new HashMap<String, AchievementListData>();
		//return AchievementsLoader.LoadAchievementsList(ach, ach.getDataFolder().getPath(), getListLocation(ach));
	}
	
	/*private String getListLocation(Achievements ach){
		AchPropertiesFile props = new AchPropertiesFile(new File(ach.getDataFolder(), "achievements.properties"));
		
		return props.getString("achievements-list", "achievements.txt", "");
	}*/
}
