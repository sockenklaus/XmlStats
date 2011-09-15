/**
 * 
 */
package de.sockenklaus.XmlStats.XmlWorkers;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.nidefawl.Achievements.PlayerAchievement;

import de.sockenklaus.XmlStats.Datasource.AchievementsDS;

/**
 * @author socrates
 *
 */
public class UserAchievements extends XmlWorker {

	AchievementsDS achDS;
	
	public UserAchievements(){
		this.achDS = new AchievementsDS();
	}
	
	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getXML(java.util.Map)
	 */
	@Override
	protected Element getXml(Map<String, List<String>> parameters) {
		Element elem_error = this.doc.createElement("error");
		elem_error.setAttribute("code", "1");
		elem_error.setTextContent("No data provided with this query!");
		
		return elem_error;
	}
	
	private Element getUserAchievement(String userName){
		Element elem_player = this.doc.createElement("user");
		elem_player.appendChild(getTextElem("name", userName));
		
		PlayerAchievement pa = achDS.getUserAchievement(userName);
		
		Element elem_achs = this.doc.createElement("achievements");
		
		for(String achName : pa.achievements.keySet()){
			Element elem_ach = this.doc.createElement("achievement");
			elem_ach.appendChild(getTextElem("name", achName));
			elem_ach.appendChild(getTextElem("count", pa.achievements.get(achName).getCount()));
			
			elem_achs.appendChild(elem_ach);
		}
		elem_player.appendChild(elem_achs);
		
		return elem_player;
	}

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getSumXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getSumXml(List<String> playerList, Map<String, List<String>> parameters) {
		if(parameters.containsKey("user")){
			return null;
		}
		else {
			Element elem_error = this.doc.createElement("error");
			elem_error.setAttribute("code", "1");
			elem_error.setTextContent("No data provided with this query!");
			
			return elem_error;
		}
	}

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getUserXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getUserXml(List<String> userList, Map<String, List<String>> parameters) {
		Element elem_users = this.doc.createElement("users");
		
		for(String userName : userList){
			
			elem_users.appendChild(this.getUserAchievement(userName));
		}
		
		return elem_users;
	}

}
