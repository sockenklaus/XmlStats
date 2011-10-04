/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import java.util.ArrayList;

/**
 * @author socrates
 *
 */
public abstract class Array extends Elem{
	protected ArrayList<Elem> childNodes;
	
	public Array(){
		this.childNodes = new ArrayList<Elem>();
	}
	
	public int getCountInt(){
		return this.childNodes.size();
	}
	
	public String getCountStr(){
		return String.valueOf(this.childNodes.size());
	}
}
