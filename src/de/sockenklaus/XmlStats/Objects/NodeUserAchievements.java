/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import com.nidefawl.Achievements.PlayerAchievement;

import de.sockenklaus.XmlStats.Datasource.AchievementsDS;
import de.sockenklaus.XmlStats.Datasource.Datasource;
import de.sockenklaus.XmlStats.Exceptions.XmlStatsException;

/**
 * @author socrates
 *
 */
public class NodeUserAchievements extends NodeArray {
	public NodeUserAchievements(String userName) throws XmlStatsException{
		super("achivements");
		
		if (Datasource.getInstance().userExists(userName)){
			PlayerAchievement pa = AchievementsDS.getInstance().getUserAchievement(userName);
			
			for(String paName : pa.achievements.keySet()){
				this.childNodes.add(new NodeUserAchievement(paName, pa.get(paName)));
			}
		}
		else throw new XmlStatsException("The given username is not valid.");
	}
}
