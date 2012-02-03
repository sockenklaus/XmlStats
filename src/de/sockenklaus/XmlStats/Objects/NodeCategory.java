/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import java.util.HashMap;

/**
 * @author socrates
 *
 */
public class NodeCategory extends NodeList {
	
	/**
	 * @param category
	 */
	public NodeCategory(String catName, HashMap<String, Integer> category) {
		super("category");
				
		this.appendChild(new NodeText("name", catName));
		this.appendChild(new NodeItems(category));
	}

}
