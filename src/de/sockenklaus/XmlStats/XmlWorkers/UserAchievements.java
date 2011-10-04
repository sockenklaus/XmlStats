/**
 * 
 */
package de.sockenklaus.XmlStats.XmlWorkers;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.nidefawl.Achievements.PlayerAchievement;

import de.sockenklaus.XmlStats.Datasource.AchievementsDS;
import de.sockenklaus.XmlStats.Exceptions.XmlStatsException;

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
	protected Element getXml(Map<String, List<String>> parameters) throws XmlStatsException {
		throw new XmlStatsException("No data provided with this query!");
	}
	
	private Element getUserAchievement(String userName){
		Element elem_player = this.doc.createElement("user");
		elem_player.appendChild(getTextElem("name", userName));
		
		PlayerAchievement pa = achDS.getUserAchievement(userName);
		
		Element elem_achs = this.doc.createElement("achievements");
		elem_achs.setAttribute("count", String.valueOf(pa.achievements.size()));
		
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
	protected Element getSumXml(List<String> playerList, Map<String, List<String>> parameters) throws XmlStatsException {
		throw new XmlStatsException("sum="+parameters.get("sum")+" does not deliver any data!");
	}

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getUserXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getUserXml(List<String> userList, Map<String, List<String>> parameters) {
		Element elem_users = this.doc.createElement("users");
		elem_users.setAttribute("count", String.valueOf(userList.size()));
		
		for(String userName : userList){
			
			elem_users.appendChild(this.getUserAchievement(userName));
		}
		
		return elem_users;
	}

}