/**
 * 
 */
package de.sockenklaus.XmlStats;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlStatsRegistry.
 *
 * @author socrates
 */
public class XmlStatsRegistry {
	private HashMap<String, Object> register = null;
	private static XmlStatsRegistry instance = null;
	
	/**
	 * Instantiates a new xml stats registry.
	 */
	private XmlStatsRegistry(){
		register = new HashMap<String, Object>();
	}
	
	/**
	 * Put.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public static void put(String key, Object value){
		if(instance == null){
			instance = new XmlStatsRegistry();
		}
		instance.register.put(key, value);
	}
	
	/**
	 * Get.
	 *
	 * @param key the key
	 * @return the object
	 */
	static public Object get(String key){
		if(instance == null){
			instance = new XmlStatsRegistry();
		}
		return instance.register.get(key);
	}
	
	/**
	 * Flush.
	 */
	public static void flush(){
		instance = null;
	}
}
