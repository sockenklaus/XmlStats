/**
 * 
 */
package de.sockenklaus.XmlStats.XmlWorkers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.nidefawl.Achievements.AchievementListData;

import de.sockenklaus.XmlStats.Datasource.AchievementsDS;

/**
 * @author socrates
 *
 */
public class AchievementsList extends XmlWorker {

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getXml(java.util.Map)
	 */
	@Override
	protected Element getXml(Map<String, List<String>> parameters) {
		HashMap<String, AchievementListData> achList = new AchievementsDS().getAchievementsList();

		Element elem_achs = this.doc.createElement("achievements");
		elem_achs.setAttribute("count", String.valueOf(achList.size()));	
		
		for(String achName : achList.keySet()){
			elem_achs.appendChild(getAchievement(achList.get(achName)));
		}
		
		return elem_achs;
	}

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getSumXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getSumXml(List<String> playerList, Map<String, List<String>> parameters) {
		return this.getXml(parameters);
	}

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getUserXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getUserXml(List<String> playerList, Map<String, List<String>> parameters) {
		return this.getXml(parameters);
	}
	
	private Element getAchievement(AchievementListData data){
		Element elem_ach = this.doc.createElement("achievement");
		elem_ach.setAttribute("enabled", data.isEnabled()?"true":"false");
		
<<<<<<< HEAD
		Element elem_achname = this.doc.createElement("name");
		elem_achname.setTextContent(data.getName());
		elem_ach.appendChild(elem_achname);
		
		Element elem_achdesc = this.doc.createElement("description");
		elem_achdesc.setTextContent(data.getDescription());
		elem_ach.appendChild(elem_achdesc);	
				
		Element elem_category = this.doc.createElement("category");
		elem_category.setTextContent(data.getCategory());
		elem_ach.appendChild(elem_category);
		
		Element elem_stat = this.doc.createElement("stat");
		elem_stat.setTextContent(data.getKey());
		elem_ach.appendChild(elem_stat);
		
		Element elem_value = this.doc.createElement("value");
		elem_value.setTextContent(String.valueOf(data.getValue()));
		elem_ach.appendChild(elem_value);
		
		Element elem_maxawards = this.doc.createElement("maxawards");
		elem_maxawards.setTextContent(String.valueOf(data.getMaxawards()));
		elem_ach.appendChild(elem_maxawards);
		
		Element elem_commands = this.doc.createElement("commands");
		elem_commands.setTextContent(data.commands.toString());
		elem_ach.appendChild(elem_commands);
		
=======
		elem_ach.appendChild(getTextElem("name", data.getName()));
		elem_ach.appendChild(getTextElem("description", data.getDescription()));
		elem_ach.appendChild(getTextElem("category", data.getCategory()));
		elem_ach.appendChild(getTextElem("stat", data.getKey()));
		elem_ach.appendChild(getTextElem("value", data.getValue()));
		elem_ach.appendChild(getTextElem("maxawards", data.getMaxawards()));
		elem_ach.appendChild(getTextElem("commands", data.commands.toString()));
			
>>>>>>> parent of 40d9bce... Pushed version to 0.4. Did some work on object based XML generation.
		return elem_ach;
		
	}

}
