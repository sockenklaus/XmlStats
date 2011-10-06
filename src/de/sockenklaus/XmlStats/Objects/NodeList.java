/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author socrates
 *
 */
public class NodeList extends Node {
	protected ArrayList<Node> childNodes;
	
	public NodeList(String tagName){
		this.childNodes = new ArrayList<Node>();
		this.tagName = tagName;
	}
	
	public void appendChild(Node child){
		this.childNodes.add(child);
	}
	
	public Element getXml(Document doc){
		Element result = super.getXml(doc);
		
		for(Node node : this.childNodes){
			result.appendChild(node.getXml(doc));
		}
		
		return result;
	}
}
