/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author socrates
 *
 */
public class Elem {
	
	protected String textContent;
	protected String elemName;
	protected ArrayList<Elem> childNodes;
	
	protected Elem(){
		this.childNodes = new ArrayList<Elem>();
	}
	
	protected Elem(String elemName){
		this();
		this.elemName = elemName;
	}
	
	protected Elem(String elemName, String textContent){
		this(elemName);
		this.textContent = textContent;
	}
	protected Elem(String elemName, Integer intContent){
		this(elemName);
		this.textContent = intContent.toString();
	}
	
	protected Element addXmlChild(String elemName, String text, Document doc){
		Element result = doc.createElement(elemName);
		result.setTextContent(text);
		return result;
	}
	
	protected Element addXmlChild(String elemName, int value, Document doc){
		Element result = doc.createElement(elemName);
		result.setTextContent(String.valueOf(value));
		return result;
	}

	/**
	 * @param doc
	 * @return
	 */
	public Element getXml(Document doc){
		Element result = doc.createElement(this.elemName);
		
		if(!this.textContent.isEmpty()) result.setTextContent(this.textContent);
		
		for (Elem child : this.childNodes){
			result.appendChild(child.getXml(doc));
		}
		
		return result;
		
	}
	
	public void setTextContent(String content){
		this.textContent = content;
	}
	
	public void addChild(Elem child){
		this.childNodes.add(child);
	}
	
	public Elem getFirstChild(){
		if (this.childNodes.size() > 0) return this.childNodes.get(0);
		else throw new IndexOutOfBoundsException();
	}
}
