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
public class NodeText extends Node {
	
	protected String textContent;
	
	public NodeText(String tagName){
		this.tagName = tagName;
	}
	
	public NodeText(String tagName, String textContent){
		this(tagName);
		this.textContent = textContent;
	}

	public NodeText(String tagName, Integer intContent) {
		this(tagName);
		this.textContent = intContent.toString();
	}

	public Element getXml(Document doc){
		Element result = super.getXml(doc);
		
		if (!this.textContent.isEmpty()) result.setTextContent(this.textContent);
		
		return result;
	}
}
