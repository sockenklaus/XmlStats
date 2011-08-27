package de.sockenklaus.XmlStats.Settings;

import java.io.File;

import de.sockenklaus.XmlStats.XmlStats;

public class Settings {

	public static final String settingsFilename = "XmlStats.conf";
	
	public static int xmlStatsPort;
	public static boolean xmlStatsEnabled;

	public static void load(XmlStats plugin) {

		SettingsFile properties = new SettingsFile(new File(plugin.getDataFolder(), settingsFilename));
	
		xmlStatsPort = properties.getInt("xmlstats-port", 8080, "port of the webserver for xml-access");
		xmlStatsEnabled = properties.getBoolean("xmlstats-enabled", false, "disabled per default to avoid unwanted port-mappings");
		
		properties.save();
	}

}
