/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nidefawl.Achievements.AchievementListData;

/**
 * @author socrates
 *
 */
public class Achievement extends Elem {
	
	private Elem name;
	private Elem description;
	private Elem category;
	private Elem stat;
	private Elem value;
	private Elem maxawards;
	private Elem commands;
	private Boolean enabled;
	
	public Achievement(AchievementListData ach){
		
		this.name = new Elem("name", ach.getName());
		this.description = new Elem("description", ach.getDescription());
		this.category = new Elem("category", ach.getCategory());
		this.stat = new Elem("stat", ach.getKey());
		this.value = new Elem("value", ach.getValue());
		this.maxawards = new Elem("maxawards", ach.getMaxawards());
		this.commands = new Elem("commands", ach.commands.toString());
		this.enabled = ach.isEnabled();
	}
	
	protected Achievement(){
		
	}
	
	public Element getXml(Document doc){
		Element result = doc.createElement("achievement");
		
		result.setAttribute("enabled", this.enabled ?"true":"false");
		
		result.appendChild(this.name.getXml(doc));
		result.appendChild(this.description.getXml(doc));
		result.appendChild(this.category.getXml(doc));
		result.appendChild(this.stat.getXml(doc));
		result.appendChild(this.value.getXml(doc));
		result.appendChild(this.maxawards.getXml(doc));
		result.appendChild(this.commands.getXml(doc));
						
		return result;
	}
}
