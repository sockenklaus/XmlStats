/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import java.util.Arrays;

import com.nidefawl.Stats.datasource.Category;

/**
 * @author socrates
 *
 */
public class NodeCategory extends NodeList {
	
	/**
	 * @param category
	 */
	public NodeCategory(String catName, Category category) {
		super("category");
		
		String[] resolveCats = new String[]{"blockdestroy", "blockcreate", "itemdrop", "itempickup"};
		Boolean resolve = Arrays.asList(resolveCats).contains(catName);
		
		this.appendChild(new NodeText("name", catName));
		this.appendChild(new NodeItems(category, resolve));
	}

}
