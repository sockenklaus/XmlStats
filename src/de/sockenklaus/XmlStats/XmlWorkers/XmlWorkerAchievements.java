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
public class XmlWorkerAchievements extends XmlWorker {

	AchievementsDS achDS;
	
	public XmlWorkerAchievements(){
		this.achDS = new AchievementsDS();
	}
	
	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getXML(java.util.Map)
	 */
	@Override
	Element getXML(Map<String, List<String>> parameters) {

			
		HashMap<String, AchievementListData> achList = achDS.getAchievementsList();

		Element elem_achs = this.doc.createElement("achievements");

		/*
		 * Hier wird das XML aufgebaut
		 */
		if(parameters.containsKey("user")){
			for (String playerName : parameters.get("user")){
				elem_achs.appendChild(getPlayerAchievement(playerName));
			}
		}
		else {
			for(String achName : achList.keySet()){
				elem_achs.appendChild(getAchievement(achList.get(achName)));
			}
		}

		/*
		 * Hier endet der XML-Aufbau
		 */
		return elem_achs;
		
	}
	
	private Element getAchievement(AchievementListData data){
		Element elem_ach = this.doc.createElement("achievement");
				
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
		
		return elem_ach;
		
	}
	
	private Element getPlayerAchievement(String playerName){
		Element elem_player = this.doc.createElement("user");
		elem_player.appendChild(getTextElem("name", playerName));
		
		return null;
	}

}
