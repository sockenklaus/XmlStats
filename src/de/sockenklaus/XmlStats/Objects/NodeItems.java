/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import java.util.HashMap;

/**
 * @author socrates
 *
 */
public class NodeItems extends NodeArray {

	/**
	 * @param category
	 */
	public NodeItems(HashMap<String, Integer> category) {
		super("items");
				
		for(String varName : category.keySet()){
			this.appendChild(new NodeItem(varName, category.get(varName)));
		}
	}	
}
