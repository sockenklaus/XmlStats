/**
 * 
 */
package de.sockenklaus.XmlStats;

import java.util.HashMap;

/**
 * @author socrates
 *
 */
public class XmlStatsRegistry {
	private HashMap<String, Object> register = null;
	private static XmlStatsRegistry instance = null;
	
	private XmlStatsRegistry(){
		register = new HashMap<String, Object>();
	}
	
	public static void put(String key, Object value){
		if(instance == null){
			instance = new XmlStatsRegistry();
		}
		instance.register.put(key, value);
	}
	
	static public Object get(String key){
		if(instance == null){
			instance = new XmlStatsRegistry();
		}
		return instance.register.get(key);
	}
}
