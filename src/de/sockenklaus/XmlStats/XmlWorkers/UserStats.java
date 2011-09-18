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

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.w3c.dom.Element;

import com.nidefawl.Stats.ItemResolver.hModItemResolver;
import com.nidefawl.Stats.datasource.Category;
import com.nidefawl.Stats.datasource.PlayerStat;

import de.sockenklaus.XmlStats.Datasource.UserstatsDS;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlWorkerUserstats.
 */
public class UserStats extends XmlWorker {
	
	/** The stats ds. */
	private UserstatsDS statsDS;
	private hModItemResolver itemResolver;
	private String[] resolveCats;
	
	/**
	 * Instantiates a new xml worker userstats.
	 */
	public UserStats(){
		this.statsDS = new UserstatsDS();
		itemResolver = new hModItemResolver(new File(statsDS.getDataFolder(),"items.txt"));
		resolveCats = new String[]{"blockdestroy", "blockcreate", "itemdrop", "itempickup"};
	}
	
	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getXML(java.util.Map)
	 */
	public Element getXml(Map<String, List<String>> parameters) {
		Element elem_error = this.doc.createElement("error");
		elem_error.setAttribute("code", "1");
		elem_error.setTextContent("No data provided with this query!");
		
		return elem_error;
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
				Element elem_item = getItemElem(valName, cat.get(valName));
				
				if(Arrays.asList(resolveCats).contains(catName)){
					elem_item.setAttribute("id", String.valueOf(itemResolver.getItem(valName)));
				}
				
				elem_items.appendChild(elem_item);
			}
		}
		return elem_player;
	}
	
	/**
	 * Gets the added up stats element.
	 *
	 * @return the added up stats element
	 */
	private Element getAddedUpStatsElement(List<String> playerList){
		HashMap<String, HashMap<String, Integer>> addedStats = statsDS.getAddedStats(playerList);
		Element elem_stats = this.doc.createElement("stats");
		Element elem_cats = this.doc.createElement("categories");
		
		elem_stats.appendChild(elem_cats);
		
		for (String catName : addedStats.keySet()){
			Element elem_cat = this.doc.createElement("category");
			Element elem_items = this.doc.createElement("items");
			
			elem_cat.appendChild(getTextElem("name", catName));
			elem_cat.appendChild(elem_items);
			elem_cats.appendChild(elem_cat);
				
			for(String entryName : addedStats.get(catName).keySet()){
				Element elem_item = this.getItemElem(entryName, addedStats.get(catName).get(entryName));
				
				if(Arrays.asList(resolveCats).contains(catName)){
					elem_item.setAttribute("id", String.valueOf(itemResolver.getItem(entryName)));
				}
				
				elem_items.appendChild(elem_item);
			}
			elem_cat.appendChild(elem_items);
		}
		
		return elem_stats;
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

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getSumXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getSumXml(List<String> playerList, Map<String, List<String>> parameters) {
		Element elem_sum = this.doc.createElement("sum");
		Element elem_users = this.doc.createElement("users");
		elem_users.setAttribute("count", String.valueOf(playerList.size()));
		
		for (String userName : playerList){
			Element elem_user = this.doc.createElement("user");
			elem_user.appendChild(getTextElem("name", userName));
			
			elem_users.appendChild(elem_user);
		}
		
		elem_sum.appendChild(elem_users);
		elem_sum.appendChild(this.getAddedUpStatsElement(playerList));
		
		return elem_sum;
	}

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getUserXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getUserXml(List<String> playerList, Map<String, List<String>> parameters) {
		Element elem_users = this.doc.createElement("users");
		elem_users.setAttribute("count", String.valueOf(playerList.size()));
		
		for(String playerName : playerList){
			elem_users.appendChild(this.getUserElement(playerName));
		}
		
		return elem_users;
	}


}
