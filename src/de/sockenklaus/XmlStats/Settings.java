package de.sockenklaus.XmlStats;

import java.io.File;

import org.bukkit.util.config.Configuration;

public class Settings {
	private static final String configFilename = "config.yml";
	private Configuration conf;
	
	public Settings(XmlStats xmlStats){
		File f = new File(xmlStats.getDataFolder(), configFilename);
		
		conf = new Configuration(f);
		if(f.exists()){
			conf.load();
		} 
		else {
			conf.setProperty("options.webserver-enabled", false);
			conf.setProperty("options.webserver-port", 9123);
			conf.setProperty("plugins.stats", true);
			conf.setProperty("plugins.users", true);
			conf.setProperty("plugins.achievements", false);
			conf.setProperty("plugins.economy", false);
			conf.save();
		}
	}
	
	public int getInt(String path){
		return conf.getInt(path, -1);
	}
	public boolean getBoolean(String path){
		return conf.getBoolean(path, false);
	}
	public String getString(String path){
		return conf.getString(path, "");
	}
	public void setProperty(String path, Object value){
		conf.setProperty(path, value);
	}
	public String getSettingsFilename(){
		return configFilename;
	}
	
	
}
