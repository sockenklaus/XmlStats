/**
 * 
 */
package de.sockenklaus.XmlStats.XmlWorkers;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import de.sockenklaus.XmlStats.Datasource.AchievementsDS;
import de.sockenklaus.XmlStats.Exceptions.XmlStatsException;
import de.sockenklaus.XmlStats.Objects.NodeUser;
import de.sockenklaus.XmlStats.Objects.NodeUserAchievements;
import de.sockenklaus.XmlStats.Objects.NodeUsers;

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
	protected Element getUserXml(List<String> userList, Map<String, List<String>> parameters) throws XmlStatsException{
		NodeUsers node_users = new NodeUsers();
		
		for(String userName : userList){
			NodeUser node_user = new NodeUser(userName);
			node_user.appendChild(new NodeUserAchievements(userName));
			node_users.appendChild(node_user);
		}
	
		return node_users.getXml(this.doc);
	}

}
