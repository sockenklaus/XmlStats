/**
 * 
 */
package de.sockenklaus.XmlStats.Datasource;

import java.util.HashMap;

import com.nidefawl.Achievements.AchievementListData;
import com.nidefawl.Achievements.Achievements;

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
	}
}
