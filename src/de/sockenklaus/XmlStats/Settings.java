/*
 * Copyright (C) [2011]  [Pascal Koenig]
*
* This program is free software; you can redistribute it and/or modify it under the terms of
* the GNU General Public License as published by the Free Software Foundation; either version
* 3 of the License, or (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License along with this program; 
* if not, see <http://www.gnu.org/licenses/>. 
*/
package de.sockenklaus.XmlStats;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class Settings.
 */
public class Settings {
	
	private static final String configFilename = "config.yml";

	private YamlConfiguration conf;
	
	/**
	 * Instantiates a new settings.
	 *
	 * @param xmlStats the xml stats
	 */
	private Settings(){
		File f = new File(XmlStats.getInstance().getDataFolder(), configFilename);
		
		if(f.exists()){
			conf = YamlConfiguration.loadConfiguration(f);
		} 
		else {
			conf.set("options.webserver-enabled", true);
			conf.set("options.webserver-port", 9123);
			conf.set("options.verbose-enabled", true);
			try {
				conf.save(f);
			}catch(IOException e){
				XmlStats.LogError("Something went wrong with the conf-file: "+e.getMessage());
			}
		}
	}
	
	public static synchronized Settings getInstance(){
		if(instance == null) instance = new Settings();
		return instance;
	}
	
	/**
	 * Gets the int.
	 *
	 * @param path the path
	 * @return the int
	 */
	public int getInt(String path){
		return this.conf.getInt(path, -1);
	}
	
	/**
	 * Gets the boolean.
	 *
	 * @param path the path
	 * @return the boolean
	 */
	public boolean getBoolean(String path){
		return this.conf.getBoolean(path, false);
	}
	
	/**
	 * Gets the string.
	 *
	 * @param path the path
	 * @return the string
	 */
	public String getString(String path){
		return this.conf.getString(path, "");
	}
	
	/**
	 * Sets the property.
	 *
	 * @param path the path
	 * @param value the value
	 */
	public void setProperty(String path, Object value){
		conf.set(path, value);
	}
	
	/**
	 * Gets the settings filename.
	 *
	 * @return the settings filename
	 */
	protected String getSettingsFilename(){
		return configFilename;
	}
	
	
}
