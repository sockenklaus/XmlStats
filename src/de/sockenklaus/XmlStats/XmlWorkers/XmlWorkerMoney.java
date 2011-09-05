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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

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
		
		Element elem_users = this.doc.createElement("users");

		/*
		 * Hier wird das XML aufgebaut
		 */

		for (String playerName : balances.keySet()){
			Element elem_user = this.doc.createElement("user");
			elem_user.appendChild(getTextElem("name", playerName));
			elem_user.appendChild(getTextElem("balance", String.valueOf(balances.get(playerName))));

			elem_users.appendChild(elem_user);
		}

		/*
		 * Hier endet der XML-Aufbau
		 */
		return elem_users;
	}
}
