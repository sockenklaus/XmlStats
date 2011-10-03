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

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

import de.sockenklaus.XmlStats.XmlStats;
import de.sockenklaus.XmlStats.Datasource.BalancesDS;
import de.sockenklaus.XmlStats.Exceptions.XmlStatsException;

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

		Element elem_users = this.doc.createElement("users");
		elem_users.setAttribute("count", String.valueOf(playerList.size()));
		
		for(String userName : playerList){
			Element elem_user = this.doc.createElement("user");
			elem_user.appendChild(getTextElem("name", userName));
			elem_user.appendChild(getTextElem("balance", String.valueOf(moneyDS.getBalance(userName))));

			elem_users.appendChild(elem_user);
		}
		
		return elem_users;
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
			
		Element elem_sum = this.doc.createElement("sum");
		Element elem_users = this.doc.createElement("users");
		elem_users.setAttribute("count", String.valueOf(userList.size()));
		elem_users.setAttribute("type", "name");
		elem_sum.appendChild(elem_users);
		
		for(String userName : userList){
			Element elem_user = this.doc.createElement("user");
			elem_user.appendChild(getTextElem("name", userName));
			elem_users.appendChild(elem_user);
			XmlStats.LogDebug("Got "+userName);
		}
		
		int sum = moneyDS.getSum(userList);
		
		elem_sum.appendChild(getTextElem("balance", String.valueOf(sum)));
		
		return elem_sum;
	}
}
