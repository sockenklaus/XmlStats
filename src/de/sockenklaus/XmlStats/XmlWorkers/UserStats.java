/*
 * Copyright (C) [2011]  [Pascal Koenig]
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

<<<<<<< HEAD
import com.nidefawl.Stats.datasource.Category;

import de.sockenklaus.XmlStats.Datasource.StatsDS;
=======
import de.sockenklaus.XmlStats.Datasource.UserstatsDS;
>>>>>>> stats2.0
import de.sockenklaus.XmlStats.Exceptions.XmlStatsException;
import de.sockenklaus.XmlStats.Objects.NodeCategories;
import de.sockenklaus.XmlStats.Objects.NodeCategory;
import de.sockenklaus.XmlStats.Objects.NodeList;
import de.sockenklaus.XmlStats.Objects.NodeUser;
import de.sockenklaus.XmlStats.Objects.NodeUsers;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlWorkerUserstats.
 */
public class UserStats extends XmlWorker {
	

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getXML(java.util.Map)
	 */
	public Element getXml(Map<String, List<String>> parameters) throws XmlStatsException {
		throw new XmlStatsException("No data provided with this query!");
	}
	

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getSumXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getSumXml(List<String> playerList, Map<String, List<String>> parameters) throws XmlStatsException {
		NodeList node_sum = new NodeList("sum");
		NodeList node_stats = new NodeList("stats");
		NodeUsers node_users = new NodeUsers();
		NodeCategories node_cats = new NodeCategories();
		
		for(String userName : playerList){
			node_users.appendChild(new NodeUser(userName));
		}
		
<<<<<<< HEAD
		HashMap<String, Category> addedStats = StatsDS.getInstance().getAddedStats(playerList);
=======
		HashMap<String, HashMap<String, Integer>> addedStats = UserstatsDS.getAddedStats(playerList);
>>>>>>> stats2.0
		
		for(String catName : addedStats.keySet()){
			NodeCategory node_cat = new NodeCategory(catName, addedStats.get(catName));
			
			node_cats.appendChild(node_cat);
		}
		
		node_sum.appendChild(node_users);
		node_stats.appendChild(node_cats);
		node_sum.appendChild(node_stats);
		
		return node_sum.getXml(this.doc);
	}

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getUserXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getUserXml(List<String> playerList, Map<String, List<String>> parameters) throws XmlStatsException {
		NodeUsers node_users = new NodeUsers();
		
		for(String userName : playerList){
			NodeUser node_user = new NodeUser(userName);
			NodeCategories node_cats = new NodeCategories(userName);
			
			node_user.appendChild(node_cats);
			node_users.appendChild(node_user);
		}
		
		return node_users.getXml(this.doc);
	}


}
