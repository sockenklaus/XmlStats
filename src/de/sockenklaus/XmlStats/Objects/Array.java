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
public abstract class Array extends NodeList {
	
	public Array(String tagName){
		super(tagName);
	}
	
	public int getCountInt(){
		return this.childNodes.size();
	}
	
	public String getCountStr(){
		return String.valueOf(this.childNodes.size());
	}
	
	public Element getXml(Document doc){
		this.attributes.put("count", String.valueOf(this.childNodes.size()));
		
		return super.getXml(doc);
	}
}
