/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import java.util.ArrayList;

/**
 * @author socrates
 *
 */
public abstract class Array {
	private ArrayList<?> childNodes;
	
	public int getCountInt(){
		return this.childNodes.size();
	}
	
	public String getCountStr(){
		return String.valueOf(this.childNodes.size());
	}
}
