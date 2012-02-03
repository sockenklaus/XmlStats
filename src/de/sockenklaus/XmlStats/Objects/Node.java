/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;


import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author socrates
 *
 */
public abstract class Node {
	
	protected String tagName;
	protected HashMap<String, String> attributes;
	
	public Node(){
		this.attributes = new HashMap<String, String>();
	}
	
	public void setAttribute(String name, String value){
		this.attributes.put(name, value);
	}
	
	/**
	 * @param name
	 * @param item
	 */
	public void setAttribute(String name, Integer value) {
		this.setAttribute(name, value.toString());
		
	}
	
	public Element getXml(Document doc){
		Element result = doc.createElement(this.tagName);
		
		for(String attrName : this.attributes.keySet()){
			result.setAttribute(attrName, this.attributes.get(attrName));
		}
		
		return result;
	}
}
