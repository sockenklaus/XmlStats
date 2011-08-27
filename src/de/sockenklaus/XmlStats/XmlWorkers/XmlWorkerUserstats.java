package de.sockenklaus.XmlStats.XmlWorkers;

import java.io.File;
import java.io.StringWriter;
import java.util.Arrays;
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
import com.nidefawl.Stats.ItemResolver.hModItemResolver;
import com.nidefawl.Stats.datasource.Category;
import com.nidefawl.Stats.datasource.PlayerStat;

import de.sockenklaus.XmlStats.Datasource.StatsDS;

public class XmlWorkerUserstats extends XmlWorker {
	
	@Override
	public String processQuery(Map<String, Object> parameters) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			DOMSource source = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			StatsDS statsDS = new StatsDS();
			
			hModItemResolver itemResolver = new hModItemResolver(new File(statsDS.getDataFolder(),"items.txt"));
			
			String[] resolveCats = {"blockdestroy", "blockcreate", "itemdrop", "itempickup"};
			
			Element root = doc.createElement("stats");
			doc.appendChild(root);
			
			/*
			 * Hier wird das XML aufgebaut
			 */
				
			for(String playerName : statsDS.getStats().keySet()){
				PlayerStat player_stats = statsDS.getStats().get(playerName);
				
				Element elem_player = doc.createElement("player");
				elem_player.setAttribute("name", playerName);
					
				for(String catName : player_stats.getCats()){
					Category cat = player_stats.get(catName);
					Element elem_cat = doc.createElement("category");
					elem_cat.setAttribute("name", catName);
						
					for(String valName : cat.stats.keySet()){
						int value = cat.get(valName);
						Element elem_value = doc.createElement("stat");
						
						elem_value.setAttribute("name", valName);
						
						if (Arrays.asList(resolveCats).contains(catName)){
							elem_value.setAttribute("id", String.valueOf(itemResolver.getItem(valName)));
						}
						elem_value.setAttribute("value", String.valueOf(value));
						
						elem_cat.appendChild(elem_value);
					}
					
					
					elem_player.appendChild(elem_cat);
				}
							
				root.appendChild(elem_player);
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

}
