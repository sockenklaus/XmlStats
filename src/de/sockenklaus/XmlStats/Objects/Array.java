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
public abstract class Array {
	protected ArrayList<Elem> childNodes;
	
	protected Array(){
		this.childNodes = new ArrayList<Elem>();
	}
	public int getCountInt(){
		return this.childNodes.size();
	}
	
	public String getCountStr(){
		return String.valueOf(this.childNodes.size());
	}

	/**
	 * @param doc
	 * @return
	 */
	public abstract Element getXml(Document doc);
}
