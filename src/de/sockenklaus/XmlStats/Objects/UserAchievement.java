/**
*
*/
package de.sockenklaus.XmlStats.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
* @author socrates
*
*/
public class UserAchievement extends Achievement {

	private String name;
	private int count;

	public UserAchievement(String paName, com.nidefawl.Achievements.Achievement achievement){
		this.name = paName;
		this.count = achievement.getCount();
	}
	
	public Element getXml(Document doc){
		Element result = doc.createElement("achievement");

		result.appendChild(this.addXmlChild("name", this.name, doc));
		result.appendChild(this.addXmlChild("count", this.count, doc));

		return result;
	}
}

