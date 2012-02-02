/**
 * 
 */
package de.sockenklaus.XmlStats;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.util.config.Configuration;

import de.sockenklaus.XmlStats.Exceptions.XmlStatsException;

/**
 * @author socrates
 *
 */
public class XSAuth {
	private static XSAuth instance;
	
	private File authKeyFile;
	private Configuration authKeyConf;
	private String currentKey;
	private String pendingKey;
	
	private XSAuth(){
		this.authKeyFile = new File(XmlStats.getInstance().getDataFolder(), "auth_keys.yml");
		this.currentKey = "";
		
		this.authKeyConf = new Configuration(this.authKeyFile);
		if(this.authKeyFile.exists()){
			
			this.authKeyConf.load();
		}
		else {
			this.authKeyConf.setProperty("keys", new ArrayList<String>());
			this.authKeyConf.save();
		}
	}
	
	public static XSAuth getInstance(){
		if(instance == null) instance = new XSAuth();
		return instance;
	}

	public boolean whitelistHasKey(String key){
		if(this.authKeyConf.getList("keys") != null && this.authKeyConf.getList("keys").contains(key)) return true;
		else return false;
	}
		
	public void authAddKey(String key){
	}
	
	public void setCurrentKey(String currentKey){
		this.currentKey = currentKey;
	}
	
	public String getCurrentKey(){
		return this.currentKey;
	}

	/**
	 * @throws XmlStatsException 
	 * 
	 */
	public void addCurrentToWhitelist() throws XmlStatsException {
		
		if(whitelistHasKey(this.currentKey)){
			throw new XmlStatsException("Current key "+this.currentKey+" is already known to the whitelist.");
		}
		
		else if(this.currentKey.equals(this.pendingKey)){
			throw new XmlStatsException("Current key "+this.currentKey+" is already awaiting clearance.");
		}
		else {
			
		}
		
	}

	/**
	 * 
	 */
	public void remCurrentFromWhitelist() {
		// TODO Auto-generated method stub
		
	}
}
