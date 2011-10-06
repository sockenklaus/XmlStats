/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author socrates
 *
 */
public abstract class NodeArray extends NodeList {
	
	public NodeArray(String tagName){
		super(tagName);
	}
	
	public Element getXml(Document doc){
		this.attributes.put("count", String.valueOf(this.childNodes.size()));
		
		return super.getXml(doc);
	}
}
