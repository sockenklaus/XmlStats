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
public class User extends Node {

	private String name;
	
	public User(String name){
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.Objects.Elem#getXml(org.w3c.dom.Document)
	 */
	@Override
	public Element getXml(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

}
