/*
 * Copyright (C) [2011]  [Pascal K�nig]
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
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
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			DOMSource source = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			
			HashMap<String, Double> balances = moneyDS.getBalances();
			
			Element root = doc.createElement("money");
			doc.appendChild(root);
			
			/*
			 * Hier wird das XML aufgebaut
			 */
			
			for (String playerName : balances.keySet()){
				Element elem_player = doc.createElement("player");
				elem_player.setAttribute("name", playerName);
				elem_player.setAttribute("balance", String.valueOf(balances.get(playerName)));
				
				root.appendChild(elem_player);
			}
			
			/*
			 * Hier endet der XML-Aufbau
			 */
			
			transformer.transform(source, result);
			return writer.toString();
		} 
		
		catch (Exception e){
			Stats.log.log(Level.SEVERE, "Something went terribly wrong!");
			Stats.log.log(Level.SEVERE, e.getMessage());
		}
		
		return "";
	}
}
