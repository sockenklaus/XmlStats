/**
 * 
 */
package de.sockenklaus.XmlStats.XmlWorkers;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
			this.factory = DocumentBuilderFactory.newInstance();
			this.builder = this.factory.newDocumentBuilder();
			this.doc = this.builder.newDocument();	
			this.source = new DOMSource(this.doc);
			this.writer = new StringWriter();
			this.result = new StreamResult(this.writer);
			this.tf = TransformerFactory.newInstance();
			this.transformer = this.tf.newTransformer();	

			HashMap<String, AchievementListData> achList = achDS.getAchievementsList();

			Element root = this.doc.createElement("xmlstats");
			Element elem_achs = this.doc.createElement("achievements");
			this.doc.appendChild(root);
			root.appendChild(elem_achs);

			/*
			 * Hier wird das XML aufgebaut
			 */
			for(String achName : achList.keySet()){
				elem_achs.appendChild(getAchievement(achList.get(achName)));
			}

			/*
			 * Hier endet der XML-Aufbau
			 */
		
			this.transformer.transform(this.source, result);
			return this.writer.toString();
		}
		catch (Exception e){
			Stats.log.log(Level.SEVERE, "Something went terribly wrong!");
			Stats.log.log(Level.SEVERE, e.getMessage());
			return "";
		}
		
		
	}
	
	private Element getAchievement(AchievementListData data){
		Element elem_ach = this.doc.createElement("achievement");
				
		Element elem_category = this.doc.createElement("category");
		elem_category.setTextContent(data.getCategory());
		elem_ach.appendChild(elem_category);
		
		Element elem_stat = this.doc.createElement("stat");
		elem_stat.setTextContent(data.getKey());
		elem_ach.appendChild(elem_stat);
		
		Element elem_value = this.doc.createElement("value");
		elem_value.setTextContent(String.valueOf(data.getValue()));
		elem_ach.appendChild(elem_value);
		
		Element elem_maxawards = this.doc.createElement("maxawards");
		elem_maxawards.setTextContent(String.valueOf(data.getMaxawards()));
		elem_ach.appendChild(elem_maxawards);
		
		Element elem_commands = this.doc.createElement("commands");
		elem_commands.setTextContent(data.commands.toString());
		elem_ach.appendChild(elem_commands);
		
		return elem_ach;
		
	}

}
