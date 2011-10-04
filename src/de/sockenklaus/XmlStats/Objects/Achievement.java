/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nidefawl.Achievements.AchievementListData;

import de.sockenklaus.XmlStats.XmlStats;

/**
 * @author socrates
 *
 */
public class Achievement extends Elem {
	
	private String name;
	private String description;
	private String category;
	private String stat;
	private Integer value;
	private Integer maxawards;
	private String commands;
	private Boolean enabled;
	
	public Achievement(AchievementListData ach){
		
		this.name = ach.getName();
		this.description = ach.getDescription();
		this.category = ach.getCategory();
		this.stat = ach.getKey();
		this.value = ach.getValue();
		this.maxawards = ach.getMaxawards();
		this.commands = ach.commands.toString();
		this.enabled = ach.isEnabled();
		XmlStats.LogDebug("End of Achievement constructor.");
	}
	
	protected Achievement(){
		
	}
	
	public Element getXml(Document doc){
		Element result = doc.createElement("achievement");
		
		result.setAttribute("enabled", this.enabled.toString());
		result.appendChild(this.addXmlChild("name", this.name, doc));
		result.appendChild(this.addXmlChild("description", this.description, doc));
		result.appendChild(this.addXmlChild("category", this.category, doc));
		result.appendChild(this.addXmlChild("stat", this.stat, doc));
		result.appendChild(this.addXmlChild("value", this.value, doc));
		result.appendChild(this.addXmlChild("maxawards", this.maxawards, doc));
		result.appendChild(this.addXmlChild("commands", this.commands, doc));
				
		return result;
	}
}
