/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import com.nidefawl.Achievements.AchievementListData;

/**
 * @author socrates
 *
 */
public class NodeAchievement extends NodeList {
	
	public NodeAchievement(){
		super("achievement");
	}
	
	public NodeAchievement(AchievementListData ach){
		super("achievement");
		
		this.childNodes.add(new NodeText("name", ach.getName()));
		this.childNodes.add(new NodeText("description", ach.getDescription()));
		this.childNodes.add(new NodeText("category", ach.getCategory()));
		this.childNodes.add(new NodeText("stat", ach.getKey()));
		this.childNodes.add(new NodeText("value", ach.getValue()));
		this.childNodes.add(new NodeText("maxawards", ach.getMaxawards()));
		this.childNodes.add(new NodeText("commands", ach.commands.toString()));
		this.attributes.put("enabled", String.valueOf(ach.isEnabled()));
	}
}
