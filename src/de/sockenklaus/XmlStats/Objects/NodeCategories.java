/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import com.nidefawl.Stats.datasource.PlayerStat;

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
		PlayerStat userStat = StatsDS.getInstance().getPlayerStat(userName);
		
		for(String catName : userStat.getCats()){
			NodeCategory node_cat = new NodeCategory(catName, userStat.get(catName));
			this.appendChild(node_cat);
		}
	}
}
