/**
 * 
 */
package de.sockenklaus.XmlStats.XmlWorkers;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.bukkit.util.config.Configuration;
import org.w3c.dom.Element;

import de.sockenklaus.XmlStats.XmlStats;
import de.sockenklaus.XmlStats.Exceptions.XmlStatsException;

/**
 * @author socrates
 *
 */
public class AuthRegister extends XmlWorker {

	private String authKeyFilename;
	private String tempAuthKeyFilname;
	
	public AuthRegister(){
		super();
		this.authKeyFilename = "auth_keys.yaml";
		this.tempAuthKeyFilname = "auth_keys_tmp.yml";
	}
	
	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getXml(java.util.Map)
	 */
	@Override
	protected Element getXml(Map<String, List<String>> parameters) throws XmlStatsException {

		if(parameters.containsKey("key")){
			for (String key : parameters.get("key")){
				XmlStats x_temp = XmlStats.getInstance();
				File authKeyFile = new File(x_temp.getDataFolder(), this.authKeyFilename);
				
				Configuration authKeyConf = new Configuration(authKeyFile);
				if(authKeyFile.exists()){
					authKeyConf.load();
					/*
					 * Hier kann geschaut werden, ob der Key in der AuthKey steht
					 * 
					 */
					return null;
				}
				else {
					/*
					 * Datei existiert nicht, Key kann nicht existieren
					 */
					File tempAuthKeyFile = new File(x_temp.getDataFolder(), this.tempAuthKeyFilname);
					
					Configuration tempAuthKeyConf = new Configuration(tempAuthKeyFile);
					if(tempAuthKeyFile.exists()){
						tempAuthKeyConf.load();
						if(tempAuthKeyConf.getList("keys") != null && tempAuthKeyConf.getList("keys").contains(key)){
							/*
							 * Key existiert schon in auth_keys_tmp... mach was
							 */
							return null;
						}
						else tempAuthKeyConf.setProperty("keys.key",key);
					}
					else {
						tempAuthKeyConf.setProperty("keys.key", key);
						/*
						 * Key wurde in auth_keys_tmp eingetragen... jetzt reagieren.
						 */
						return null;
					}
					
					tempAuthKeyConf.save();
				}
			}
			XmlStats.LogWarn("auth_register: There should be a key, but acutally there is no...");
			throw new XmlStatsException("auth_register: There should be a key, but actually there is no...");
			
		}
		else throw new XmlStatsException("No key given!");
		
	}

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getSumXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getSumXml(List<String> playerList, Map<String, List<String>> parameters) throws XmlStatsException {
		return this.getXml(parameters);
	}

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getUserXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getUserXml(List<String> playerList, Map<String, List<String>> parameters) throws XmlStatsException {
		return this.getXml(parameters);
	}

}
