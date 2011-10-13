/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import de.sockenklaus.XmlStats.XmlStats;
import de.sockenklaus.XmlStats.Datasource.Datasource;
import de.sockenklaus.XmlStats.Exceptions.XmlStatsException;

/**
 * @author socrates
 *
 */
public class NodeUser extends NodeList {
	
	public NodeUser(String name) throws XmlStatsException{
		super("user");
		if (Datasource.getInstance().userExists(name)){
			Server server = XmlStats.getInstance().getServer();
			Player player = server.getPlayer(name);

			String status = (player != null && player.isOnline()) ?"online":"offline";
			this.attributes.put("status", status);
			this.childNodes.add(new NodeText("name", name));
		}
		else {
			throw new XmlStatsException("The given user doesn't exist!");
		}
		
	}
}
