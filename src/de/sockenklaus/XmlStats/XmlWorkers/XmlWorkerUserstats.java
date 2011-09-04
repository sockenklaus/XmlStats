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
import java.util.Map;
import java.util.logging.Level;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

import com.nidefawl.Stats.Stats;
import com.nidefawl.Stats.datasource.Category;
import com.nidefawl.Stats.datasource.PlayerStat;

import de.sockenklaus.XmlStats.Datasource.UserstatsDS;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlWorkerUserstats.
 */
public class XmlWorkerUserstats extends XmlWorker {
	
	/** The stats ds. */
	private UserstatsDS statsDS;
	
	/**
	 * Instantiates a new xml worker userstats.
	 */
	public XmlWorkerUserstats(){
		this.statsDS = new UserstatsDS();
	}
	
	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getXML(java.util.Map)
	 */
	public String getXML(Map<String, List<String>> parameters) {
		try {
			this.factory = DocumentBuilderFactory.newInstance();
			this.builder = this.factory.newDocumentBuilder();
			this.doc = this.builder.newDocument();	
			this.source = new DOMSource(this.doc);
			this.writer = new StringWriter();
			this.result = new StreamResult(writer);
			this.tf = TransformerFactory.newInstance();
			this.transformer = this.tf.newTransformer();

			Element root = this.doc.createElement("xmlstats");
			Element elem_userstats = this.doc.createElement("userstats");
			this.doc.appendChild(root);
			root.appendChild(elem_userstats);

			/*
			 * Hier wird das XML aufgebaut
			 */
			if (!parameters.containsKey("player")){
				// Generate a summarized XML
				elem_userstats.setAttribute("type", "sum");
				elem_userstats.appendChild(getAddedUpStatsElement());

			}
			else {
				// Generate the XML for the given user(s)
				for(String playerName : statsDS.fetchAllPlayers()){
					if (parameters.containsKey("player") && parameters.get("player").contains(playerName.toLowerCase())){
						elem_userstats.appendChild(getPlayerElement(playerName));
					}
				}
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
			return "";
		}
	}
	
	/**
	 * Build a XML subtree for the given player.
	 *
	 * @param playerName the player name
	 * @paramthis.doc thethis.doc
	 * @return 				Returns a XML subtree for the given playerName.
	 */
	private Element getPlayerElement(String playerName){
		PlayerStat player_stats = statsDS.getPlayerStat(playerName);
		
		Element elem_player = this.doc.createElement("player");
		Element elem_cats = this.doc.createElement("categories");
	
		elem_player.appendChild(getTextElem("name", playerName));
		elem_player.appendChild(elem_cats);
		
		for(String catName : player_stats.getCats()){
			Category cat = player_stats.get(catName);
			Element elem_cat = this.doc.createElement("category");
			Element elem_items = this.doc.createElement("items");
			
			elem_cat.appendChild(getTextElem("name", catName));
			elem_cat.appendChild(elem_items);
			elem_cats.appendChild(elem_cat);
			
			for(String valName : cat.stats.keySet()){
				int value = cat.get(valName);
				Element elem_item = this.doc.createElement("item");
								
				elem_item.appendChild(getTextElem("name", valName));
				elem_item.appendChild(getTextElem("value", String.valueOf(value)));
								
				elem_items.appendChild(elem_item);
			}
		}
		return elem_player;
	}
	
	private Element getAddedUpStatsElement(){
		HashMap<String, HashMap<String, Integer>> addedStats = statsDS.getAddedStats();
		Element elem_player = this.doc.createElement("player");
		Element elem_cats = this.doc.createElement("categories");
		
		elem_player.appendChild(elem_cats);
		
		for (String catName : addedStats.keySet()){
			Element elem_cat = this.doc.createElement("category");
			Element elem_items = this.doc.createElement("items");
			
			elem_cat.appendChild(getTextElem("name", catName));
			elem_cat.appendChild(elem_items);
			elem_cats.appendChild(elem_cat);
				
			for(String entryName : addedStats.get(catName).keySet()){
				Element elem_item = this.doc.createElement("item");
				
				elem_item.appendChild(getTextElem("name", entryName));
				elem_item.appendChild(getTextElem("value", String.valueOf(addedStats.get(catName).get(entryName))));
				
				elem_items.appendChild(elem_item);
			}
			elem_cat.appendChild(elem_items);
		}
		
		return elem_player;
	}
	
	private Element getTextElem(String elemName, String text){
		Element result = this.doc.createElement(elemName);
		result.setTextContent(text);
		return result;
	}
	
	private Element getItemElem(String key, String value){
		return null;
	}
}
