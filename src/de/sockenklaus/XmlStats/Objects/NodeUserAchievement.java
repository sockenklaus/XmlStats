/**
*
*/
package de.sockenklaus.XmlStats.Objects;

import com.nidefawl.Achievements.Achievement;

/**
* @author socrates
*
*/
public class NodeUserAchievement extends NodeList {

	public NodeUserAchievement(String paName, Achievement achievement){
		super("achievement");
		this.childNodes.add(new NodeText("name", paName));
		this.childNodes.add(new NodeText("count", achievement.getCount()));
	}
}

