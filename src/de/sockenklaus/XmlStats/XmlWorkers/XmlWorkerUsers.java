package de.sockenklaus.XmlStats.XmlWorkers;

import java.io.StringWriter;
import java.util.Map;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nidefawl.Stats.Stats;

import de.sockenklaus.XmlStats.Datasource.UsersDS;

public class XmlWorkerUsers extends XmlWorker {
	
	@Override
	public String processQuery(Map<String, Object> parameters) {
		UsersDS users = new UsersDS();
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			DOMSource source = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			
			Element root = doc.createElement("players");
			root.setAttribute("count", String.valueOf(users.getAllPlayers().size()));
			doc.appendChild(root);
			
			
			/*
			 * Hier wird das XML aufgebaut
			 */
				
			for(String playerName : users.getAllPlayers()){
				
				Element elem_player = doc.createElement("player");
				elem_player.setAttribute("name", playerName);
							
				root.appendChild(elem_player);
			}
			/*
			 * Hier endet der XML-Aufbau
			 */
			
			transformer.transform(source, result);
			return writer.toString();
		} 
		
		catch (Exception e)
		{
			Stats.log.log(Level.SEVERE, "Something went terribly wrong!");
			Stats.log.log(Level.SEVERE, e.getMessage());
		}
		
		return "";
	}
	
}
