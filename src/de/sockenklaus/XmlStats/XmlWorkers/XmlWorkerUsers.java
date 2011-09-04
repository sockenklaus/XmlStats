/*
 * Copyright (C) [2011]  [Pascal König]
*
* This program is free software; you can redistribute it and/or modify it under the terms of
* the GNU General Public License as published by the Free Software Foundation; either version
* 3 of the License, or (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License along with this program; 
* if not, see <http://www.gnu.org/licenses/>. 
*/
package de.sockenklaus.XmlStats.XmlWorkers;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.bukkit.entity.Player;
import org.w3c.dom.Element;

import com.nidefawl.Stats.Stats;

import de.sockenklaus.XmlStats.XmlStats;
import de.sockenklaus.XmlStats.XmlStatsRegistry;
import de.sockenklaus.XmlStats.Datasource.UsersDS;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlWorkerUsers.
 */
public class XmlWorkerUsers extends XmlWorker {
	
	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getXML(java.util.Map)
	 */
	@Override
	public String getXML(Map<String, List<String>> parameters) {
		UsersDS users = new UsersDS();
		
		try {
			this.factory = DocumentBuilderFactory.newInstance();
			this.builder = this.factory.newDocumentBuilder();
			this.doc = this.builder.newDocument();	
			this.source = new DOMSource(this.doc);
			this.writer = new StringWriter();
			this.result = new StreamResult(writer);
			this.tf = TransformerFactory.newInstance();
			this.transformer = tf.newTransformer();		
		
			Element root = this.doc.createElement("xmlstats");
			Element elem_users = this.doc.createElement("users");
			elem_users.setAttribute("count", String.valueOf(users.getAllPlayers().size()));
			this.doc.appendChild(root);
			root.appendChild(elem_users);

			/*
			 * Get list online player names
			 */
			XmlStats pluginTemp= (XmlStats)XmlStatsRegistry.get("xmlstats");

			Player[] onlinePlayers = pluginTemp.getServer().getOnlinePlayers();
			List<String> onlinePlayerNames = new ArrayList<String>();

			if (onlinePlayers != null){
				for (int i = 0; i < onlinePlayers.length; i++){
					onlinePlayerNames.add(onlinePlayers[i].getName());
				}
			}
			/*
			 * Got list of online player names
			 */

			/*
			 * Hier wird das XML aufgebaut
			 */

			for(String playerName : users.getAllPlayers()){

				Element elem_player = this.doc.createElement("player");
				elem_player.setTextContent(playerName);

				elem_player.setAttribute("status", onlinePlayerNames.contains(playerName) ? "online":"offline");

				elem_users.appendChild(elem_player);
			}
			/*
			 * Hier endet der XML-Aufbau
			 */
			transformer.transform(source, result);
			return writer.toString();
		} 
		
		catch (Exception e)
		{
			Stats.log.log(Level.SEVERE, "Something went terribly wrong!");
			Stats.log.log(Level.SEVERE, e.getMessage());
			return "";
		}		
	}
	
}
