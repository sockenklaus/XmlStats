/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

/**
 * @author socrates
 *
 */
public class NodeItem extends NodeList {
	
	/**
	 * @param varName
	 * @param i
	 */
	public NodeItem(String varName, int value) {
		super("item");
		
		this.appendChild(new NodeText("name", varName));
		this.appendChild(new NodeText("value", value));
	}
}
