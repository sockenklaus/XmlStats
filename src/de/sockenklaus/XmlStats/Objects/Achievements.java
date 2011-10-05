/**
 * 
 */
package de.sockenklaus.XmlStats.Objects;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nidefawl.Achievements.AchievementListData;

import de.sockenklaus.XmlStats.Datasource.AchievementsDS;

/**
 * @author socrates
 *
 */
public class Achievements extends Array {
	//protected ArrayList<Achievement> childNodes;
	
	public Achievements(){
		super();
		AchievementsDS ads = new AchievementsDS();
		
		HashMap<String, AchievementListData> achList = ads.getAchievementsList();
		
		for(String achName : achList.keySet()){
			this.childNodes.add(new Achievement(achList.get(achName)));
		}
	}
	
	public Element getXml(Document doc){
		Element result = doc.createElement("achievements");
		result.setAttribute("count", this.getCountStr());
		
		for(Elem ach : this.childNodes){
			result.appendChild(ach.getXml(doc));
		}
		
		return result;
	}
}
