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
import java.util.Map;
import java.util.List;

import org.w3c.dom.Element;

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
	public Element getXML(Map<String, List<String>> parameters) {

		Element elem_userstats = this.doc.createElement("userstats");
		
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
				if (parameters.containsKey("user") && parameters.get("user").contains(playerName.toLowerCase())){
					elem_userstats.appendChild(getUserElement(playerName));
				}
			}
		}
		/*
		 * Hier endet der XML-Aufbau
		 */
		return elem_userstats;
	}
	
	/**
	 * Build a XML subtree for the given player.
	 *
	 * @param playerName the player name
	 * @return 				Returns a XML subtree for the given playerName.
	 * @paramthis.doc thethis.doc
	 */
	private Element getUserElement(String playerName){
		PlayerStat player_stats = statsDS.getPlayerStat(playerName);
		
		Element elem_player = this.doc.createElement("user");
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
				elem_items.appendChild(getItemElem(valName, cat.get(valName)));
			}
		}
		return elem_player;
	}
	
	/**
	 * Gets the added up stats element.
	 *
	 * @return the added up stats element
	 */
	private Element getAddedUpStatsElement(){
		HashMap<String, HashMap<String, Integer>> addedStats = statsDS.getAddedStats();
		Element elem_player = this.doc.createElement("user");
		Element elem_cats = this.doc.createElement("categories");
		
		elem_player.appendChild(elem_cats);
		
		for (String catName : addedStats.keySet()){
			Element elem_cat = this.doc.createElement("category");
			Element elem_items = this.doc.createElement("items");
			
			elem_cat.appendChild(getTextElem("name", catName));
			elem_cat.appendChild(elem_items);
			elem_cats.appendChild(elem_cat);
				
			for(String entryName : addedStats.get(catName).keySet()){
				elem_items.appendChild(getItemElem(entryName, addedStats.get(catName).get(entryName)));
			}
			elem_cat.appendChild(elem_items);
		}
		
		return elem_player;
	}
		
	/**
	 * Gets the item elem.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the item elem
	 */
	private Element getItemElem(String key, int value){
		Element elem_item = this.doc.createElement("item");
		
		elem_item.appendChild(getTextElem("name", key));
		elem_item.appendChild(getTextElem("value", String.valueOf(value)));
		
		return elem_item;
	}
}
