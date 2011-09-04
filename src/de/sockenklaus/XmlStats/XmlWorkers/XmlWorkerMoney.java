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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

import com.nidefawl.Stats.Stats;

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
	public String getXML(Map<String, List<String>> parameters) {
		
		try {
			this.factory = DocumentBuilderFactory.newInstance();
			this.builder = this.factory.newDocumentBuilder();
			this.doc = this.builder.newDocument();	
			this.source = new DOMSource(this.doc);
			this.writer = new StringWriter();
			this.result = new StreamResult(this.writer);
			this.tf = TransformerFactory.newInstance();
			this.transformer = this.tf.newTransformer();

			HashMap<String, Double> balances = moneyDS.getBalances();

			Element root = this.doc.createElement("xmlstats");
			Element elem_money = this.doc.createElement("money");
			this.doc.appendChild(root);
			root.appendChild(elem_money);


			/*
			 * Hier wird das XML aufgebaut
			 */

			for (String playerName : balances.keySet()){
				Element elem_player = this.doc.createElement("player");
				elem_player.setAttribute("name", playerName);
				elem_player.setAttribute("balance", String.valueOf(balances.get(playerName)));

				elem_money.appendChild(elem_player);
			}

			/*
			 * Hier endet der XML-Aufbau
			 */
			transformer.transform(this.source, result);
			return this.writer.toString();
		} 
		
		catch (Exception e){
			Stats.log.log(Level.SEVERE, "Something went terribly wrong!");
			Stats.log.log(Level.SEVERE, e.getMessage());
			return "";
		}
	}
}
