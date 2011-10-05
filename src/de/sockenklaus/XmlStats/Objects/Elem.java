/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author socrates
 *
 */
public abstract class Elem {
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
	public abstract Node getXml(Document doc);
}
