/**
*
*/
package de.sockenklaus.XmlStats.Objects;

import de.sockenklaus.XmlStats.XmlStats;

/**
* @author socrates
*
*/
public class UserAchievement extends Achievement {

	public UserAchievement(String paName, com.nidefawl.Achievements.Achievement achievement){
		this.childNodes.add(new NodeText("name", paName));
		this.childNodes.add(new NodeText("count", achievement.getCount()));
		XmlStats.LogDebug("UserAchievement-const castet.");
	}
}

