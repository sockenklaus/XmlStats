/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nidefawl.Achievements.PlayerAchievement;

import de.sockenklaus.XmlStats.Datasource.AchievementsDS;
import de.sockenklaus.XmlStats.Datasource.Datasource;
import de.sockenklaus.XmlStats.Exceptions.XmlStatsException;

/**
 * @author socrates
 *
 */
public class UserAchievements extends Achievements {
	public UserAchievements(String userName) throws XmlStatsException{
		AchievementsDS ads = new AchievementsDS();
		
		if (Datasource.userExists(userName)){
			PlayerAchievement pa = ads.getUserAchievement(userName);
			
			for(String paName : pa.achievements.keySet()){
				this.childNodes.add(new UserAchievement(paName, pa.get(paName)));
			}
		}
		else throw new XmlStatsException("The given username is not valid.");
	}
	
	public Element getXml(Document doc){
		Element result = doc.createElement("achievements");
		result.setAttribute("count", this.getCountStr());
		
		for(Elem ach : this.childNodes){
			result.appendChild(((UserAchievement)ach).getXml(doc));
		}
		
		return result;
	}
}
