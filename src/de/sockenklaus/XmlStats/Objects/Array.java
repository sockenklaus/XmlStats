/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

/**
 * @author socrates
 *
 */
public abstract class Array extends Elem{
	
	public int getCountInt(){
		return this.childNodes.size();
	}
	
	public String getCountStr(){
		return String.valueOf(this.childNodes.size());
	}
}
