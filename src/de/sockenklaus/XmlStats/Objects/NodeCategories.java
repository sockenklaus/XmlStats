/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import java.util.HashMap;

import de.sockenklaus.XmlStats.Datasource.StatsDS;

/**
 * @author socrates
 *
 */
public class NodeCategories extends NodeArray {

	public NodeCategories(){
		super("categories");
	}
	/**
	 * @param tagName
	 */
	public NodeCategories(String userName) {
		this();

		HashMap<String, HashMap<String, Integer>> userStat = UserstatsDS.getStats(userName);
		
		for(String catName : userStat.keySet()){
			NodeCategory node_cat = new NodeCategory(catName, userStat.get(catName));
			this.appendChild(node_cat);
		}
	}
}
