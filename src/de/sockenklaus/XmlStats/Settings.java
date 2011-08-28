/*
 * Copyright (C) [2011]  [Pascal König]
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

import org.bukkit.util.config.Configuration;

// TODO: Auto-generated Javadoc
/**
 * The Class Settings.
 */
public class Settings {
	
	private static final String configFilename = "config.yml";
	private Configuration conf;
	
	/**
	 * Instantiates a new settings.
	 *
	 * @param xmlStats the xml stats
	 */
	public Settings(XmlStats xmlStats){
		File f = new File(xmlStats.getDataFolder(), configFilename);
		
		conf = new Configuration(f);
		if(f.exists()){
			conf.load();
		} 
		else {
			conf.setProperty("options.webserver-enabled", false);
			conf.setProperty("options.webserver-port", 9123);
			conf.save();
		}
	}
	
	/**
	 * Gets the int.
	 *
	 * @param path the path
	 * @return the int
	 */
	public int getInt(String path){
		return conf.getInt(path, -1);
	}
	
	/**
	 * Gets the boolean.
	 *
	 * @param path the path
	 * @return the boolean
	 */
	public boolean getBoolean(String path){
		return conf.getBoolean(path, false);
	}
	
	/**
	 * Gets the string.
	 *
	 * @param path the path
	 * @return the string
	 */
	public String getString(String path){
		return conf.getString(path, "");
	}
	
	/**
	 * Sets the property.
	 *
	 * @param path the path
	 * @param value the value
	 */
	public void setProperty(String path, Object value){
		conf.setProperty(path, value);
	}
	
	/**
	 * Gets the settings filename.
	 *
	 * @return the settings filename
	 */
	public String getSettingsFilename(){
		return configFilename;
	}
	
	
}
