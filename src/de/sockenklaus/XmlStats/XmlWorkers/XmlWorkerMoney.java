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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import de.sockenklaus.XmlStats.XmlStats;
import de.sockenklaus.XmlStats.Datasource.MoneyDS;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlWorkerMoney.
 */
public class XmlWorkerMoney extends XmlWorker {

	private MoneyDS moneyDS;
	
	public XmlWorkerMoney(){
		this.moneyDS = new MoneyDS();
	}
	
	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getXML(java.util.Map)
	 */
	@Override
	public Element getXML(Map<String, List<String>> parameters) {
		
		HashMap<String, Double> balances = moneyDS.getBalances();
		
		if (parameters.containsKey("user")){
			Element elem_users = this.doc.createElement("users");
			if(parameters.get("user").contains("*")){
				
				for (String playerName : balances.keySet()){
					Element elem_user = this.doc.createElement("user");
					elem_user.appendChild(getTextElem("name", playerName));
					elem_user.appendChild(getTextElem("balance", String.valueOf(balances.get(playerName))));

					elem_users.appendChild(elem_user);
				}
				
			}
			else {
				for(String playerName : parameters.get("user")){
					Element elem_user = this.doc.createElement("user");
					elem_user.appendChild(getTextElem("name", playerName));
					elem_user.appendChild(getTextElem("balance", String.valueOf(balances.get(playerName))));
					
					elem_users.appendChild(elem_user);
				}
			}
			return elem_users;
		}
		
		else if (parameters.containsKey("sum")){
			XmlStats.LogDebug("Entering the sum part");
			Element elem_sum = this.doc.createElement("sum");
			Element elem_users = this.doc.createElement("users");
			elem_users.setAttribute("type", "name");
			elem_sum.appendChild(elem_users);
			
			
			if(parameters.get("sum").contains("*")){
				List<String> userList = new ArrayList<String>();
				for(String userName : balances.keySet()){
					Element elem_user = this.doc.createElement("user");
					elem_user.appendChild(getTextElem("name", userName));
					elem_users.appendChild(elem_user);
					XmlStats.LogDebug("Got "+userName);
					userList.add(userName);
				}
				
				int sum = moneyDS.getSum(userList);
				
				elem_sum.appendChild(getTextElem("balance", String.valueOf(sum)));
			}
			
			else {
				
				for(String userName : parameters.get("sum")){
					Element elem_user = this.doc.createElement("user");
					elem_user.appendChild(getTextElem("name", userName));
					elem_users.appendChild(elem_user);
				}
				
				int sum = moneyDS.getSum(parameters.get("sum"));
				
				elem_sum.appendChild(getTextElem("balance", String.valueOf(sum)));
			}
		
			
			return elem_sum;
		}
		return this.doc.createElement("users");
	}
}
