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

import java.util.Map;
import java.util.List;

import org.w3c.dom.Element;

import de.sockenklaus.XmlStats.Datasource.Datasource;
import de.sockenklaus.XmlStats.Exceptions.XmlStatsException;
import de.sockenklaus.XmlStats.Objects.NodeUser;
import de.sockenklaus.XmlStats.Objects.NodeUsers;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlWorkerUsers.
 */
public class UserList extends XmlWorker {
	
	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getXML(java.util.Map)
	 */
	@Override
	public Element getXml(Map<String, List<String>> parameters) throws XmlStatsException {
	
		NodeUsers node_users = new NodeUsers();

		for(String playerName : Datasource.fetchAllPlayers()){
			node_users.appendChild(new NodeUser(playerName));
		}

		return node_users.getXml(this.doc);
	}

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getSumXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getSumXml(List<String> playerList, Map<String, List<String>> parameters) throws XmlStatsException {
		return this.getXml(parameters);
	}

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getUserXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getUserXml(List<String> playerList, Map<String, List<String>> parameters) throws XmlStatsException {
		return this.getXml(parameters);
	}
	
}
