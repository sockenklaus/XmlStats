/**
 * 
 */
package de.sockenklaus.XmlStats.XmlWorkers;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
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

import com.nidefawl.Achievements.AchievementListData;
import com.nidefawl.Stats.Stats;

import de.sockenklaus.XmlStats.Datasource.AchievementsDS;

/**
 * @author socrates
 *
 */
public class XmlWorkerAchievements extends XmlWorker {

	AchievementsDS achDS;
	
	public XmlWorkerAchievements(){
		this.achDS = new AchievementsDS();
	}
	
	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getXML(java.util.Map)
	 */
	@Override
	String getXML(Map<String, List<String>> parameters) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			DOMSource source = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			
			HashMap<String, AchievementListData> achList = achDS.getAchievementsList();
			
			Element root = doc.createElement("achievements");
			doc.appendChild(root);
			
			/*
			 * Hier wird das XML aufgebaut
			 */
			for(String achName : achList.keySet()){
				root.appendChild(getAchievement(doc, achList.get(achName)));
			}
			
			/*
			 * Hier endet der XML-Aufbau
			 */
			
			transformer.transform(source, result);
			return writer.toString();
		} 
		
		catch (Exception e){
			Stats.log.log(Level.SEVERE, "Something went terribly wrong!");
			Stats.log.log(Level.SEVERE, e.getMessage());
		}
		
		return "";
	}
	
	private Element getAchievement(Document doc, AchievementListData data){
		Element elem_ach = doc.createElement("achievement");
		elem_ach.setAttribute("enabled", String.valueOf(data.isEnabled()));
		
		Element elem_name = doc.createElement("name");
		elem_name.setTextContent(data.getName());
		elem_ach.appendChild(elem_name);
		
		Element elem_description = doc.createElement("description");
		elem_description.setTextContent(data.getDescription());
		elem_ach.appendChild(elem_description);
		
		Element elem_category = doc.createElement("category");
		elem_category.setTextContent(data.getCategory());
		elem_ach.appendChild(elem_category);
		
		Element elem_stat = doc.createElement("stat");
		elem_stat.setTextContent(data.getKey());
		elem_ach.appendChild(elem_stat);
		
		Element elem_value = doc.createElement("value");
		elem_value.setTextContent(String.valueOf(data.getValue()));
		elem_ach.appendChild(elem_value);
		
		Element elem_maxawards = doc.createElement("maxawards");
		elem_maxawards.setTextContent(String.valueOf(data.getMaxawards()));
		elem_ach.appendChild(elem_maxawards);
		
		Element elem_commands = doc.createElement("commands");
		elem_commands.setTextContent(data.commands.toString());
		elem_ach.appendChild(elem_commands);
		
		return elem_ach;
		
	}

}
