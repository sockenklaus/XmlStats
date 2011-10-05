/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import java.util.HashMap;

import com.nidefawl.Achievements.AchievementListData;

import de.sockenklaus.XmlStats.Datasource.AchievementsDS;

/**
 * @author socrates
 *
 */
public class Achievements extends Array {

	public Achievements(){
		super("achievements");
		AchievementsDS ads = new AchievementsDS();
		
		HashMap<String, AchievementListData> achList = ads.getAchievementsList();
		
		for(String achName : achList.keySet()){
			this.childNodes.add(new Achievement(achList.get(achName)));
		}
	}
}
