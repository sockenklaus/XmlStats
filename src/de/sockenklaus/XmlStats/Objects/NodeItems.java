/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import java.io.File;

import com.nidefawl.Stats.ItemResolver.hModItemResolver;
import com.nidefawl.Stats.datasource.Category;

import de.sockenklaus.XmlStats.Datasource.UserstatsDS;

/**
 * @author socrates
 *
 */
public class NodeItems extends NodeArray {

	/**
	 * @param category
	 */
	public NodeItems(Category category, Boolean resolve) {
		super("items");
		
		hModItemResolver itemResolver = new hModItemResolver(new File(UserstatsDS.getDataFolder(),"items.txt"));
				
		for(String varName : category.getEntries()){
			NodeItem node_item = new NodeItem(varName, category.get(varName));
			
			if(resolve){
				node_item.setAttribute("id", itemResolver.getItem(varName));
			}
			
			this.appendChild(new NodeItem(varName, category.get(varName)));
		}
	}	
}
