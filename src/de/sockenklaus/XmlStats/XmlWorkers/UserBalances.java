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

import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import de.sockenklaus.XmlStats.Datasource.BalancesDS;
import de.sockenklaus.XmlStats.Exceptions.XmlStatsException;
import de.sockenklaus.XmlStats.Objects.NodeList;
import de.sockenklaus.XmlStats.Objects.NodeText;
import de.sockenklaus.XmlStats.Objects.NodeUser;
import de.sockenklaus.XmlStats.Objects.NodeUsers;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlWorkerMoney.
 */
public class UserBalances extends XmlWorker {

	private BalancesDS moneyDS;
	
	public UserBalances(){
		this.moneyDS = new BalancesDS();
	}
	
	
	protected Element getUserXml(List<String> playerList, Map<String, List<String>> parameters) throws XmlStatsException {

		NodeUsers node_users = new NodeUsers();
				
		for(String userName : playerList){
			NodeUser node_user = new NodeUser(userName);
			node_user.appendChild(new NodeText("balance", moneyDS.getBalance(userName)));
			
			node_users.appendChild(node_user);
		}
		
		return node_users.getXml(this.doc);
	}
	
	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getXML(java.util.Map)
	 */
	@Override
	public Element getXml(Map<String, List<String>> parameters) throws XmlStatsException {
		throw new XmlStatsException("No data provided with this query!");
	}


	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getSumXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getSumXml(List<String> userList, Map<String, List<String>> parameters) throws XmlStatsException {
		
		NodeList node_sum = new NodeList("sum");
		NodeUsers node_users = new NodeUsers();
		
		node_sum.appendChild(node_users);
		
		for(String userName : userList){
			NodeUser node_user = new NodeUser(userName);
			node_users.appendChild(node_user);
		}
		
		int sum = moneyDS.getSum(userList);
		node_sum.appendChild(new NodeText("balance", sum));
		
		return node_sum.getXml(this.doc);
	}
}
