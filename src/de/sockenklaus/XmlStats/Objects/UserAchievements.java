/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nidefawl.Achievements.PlayerAchievement;

import de.sockenklaus.XmlStats.XmlStats;
import de.sockenklaus.XmlStats.Datasource.AchievementsDS;
import de.sockenklaus.XmlStats.Datasource.Datasource;
import de.sockenklaus.XmlStats.Exceptions.XmlStatsException;

/**
 * @author socrates
 *
 */
public class UserAchievements extends Achievements {
	public UserAchievements(String userName) throws XmlStatsException{
		super();
		AchievementsDS ads = new AchievementsDS();
		
		if (Datasource.userExists(userName)){
			XmlStats.LogDebug("Achievements-const castet... user exists");
			PlayerAchievement pa = ads.getUserAchievement(userName);
			
			for(String paName : pa.achievements.keySet()){
				this.childNodes.add(new UserAchievement(paName, pa.get(paName)));
				XmlStats.LogDebug("Added Achievement.");
			}
		}
		else throw new XmlStatsException("The given username is not valid.");
	}
}
