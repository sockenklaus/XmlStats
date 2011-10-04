/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nidefawl.Achievements.PlayerAchievement;

import de.sockenklaus.XmlStats.Datasource.AchievementsDS;

/**
 * @author socrates
 *
 */
public class UserAchievements extends Achievements {
	public UserAchievements(String userName){
		AchievementsDS ads = new AchievementsDS();
		
		PlayerAchievement pa = ads.getUserAchievement(userName);
		
		for(String paName : pa.achievements.keySet()){
			this.childNodes.add(new UserAchievement(paName, pa.get(paName)));
		}
	}
	
	public Element getXml(Document doc){
		Element result = doc.createElement("achievements");
		result.setAttribute("count", this.getCountStr());
		
		for(Achievement ach : this.childNodes){
			result.appendChild(((UserAchievement)ach).getXml(doc));
		}
		
		return result;
	}
}
