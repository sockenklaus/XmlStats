package de.sockenklaus.XmlStats;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import com.nidefawl.Stats.Stats;

import de.sockenklaus.XmlStats.Settings.Settings;

public class XmlStats extends JavaPlugin {

	public final static Logger log = Logger.getLogger("Minecraft");
	public final static double version = 0.01;
	public final static String logprefix = "[XmlStats-" + version + "]";
	public boolean enabled = false;
	private static Stats statsPlugin;
	private static Server serverRef;
	public WebServer xmlQueryServer;
	
	
	@Override
	public void onDisable() {
		if(enabled && xmlQueryServer.serverRunning()){
			enabled = false;
			
			xmlQueryServer.stopServer();
			
			getServer().getScheduler().cancelTasks(this);
			
		}
		LogInfo("Plugin Disabled");
	}

	@Override
	public void onEnable() {
		
		getDataFolder().mkdirs();
		statsPlugin = (Stats)getServer().getPluginManager().getPlugin("Stats");
		serverRef = getServer();
		
		Settings.load(this);
		
		if (Settings.xmlStatsEnabled){
			if (getServer().getPluginManager().isPluginEnabled("Stats")){
				try {
					xmlQueryServer = new WebServer(Settings.xmlStatsPort);
					
					enabled = true;
					LogInfo("Plugin Enabled");
				}
				catch (Exception ex){
					LogError("Fehler beim Erstellen des Webservers:");
					LogError(ex.getMessage());
				}
				
			}
			else {
				LogError("Stats-Plugin laeuft nicht... Breche ab...");
			}
		}
		else {
			LogError("Plugin ist derzeit in der "+getDataFolder().getPath()+"/"+Settings.settingsFilename+" deaktiviert.");
		}
		
		
	}
	
	public static void LogError(String Message) {
		log.log(Level.SEVERE, logprefix + " " + Message);
	}

	public static void LogInfo(String Message) {
		log.info(logprefix + " " + Message);
	}
	
	public static void LogWarn(String Message){
		log.log(Level.WARNING, logprefix + " "+ Message);
	}
	
	public static Stats getStatsPlugin(){
		return statsPlugin;
	}
	
	public static Server getServerRef(){
		return serverRef;
	}

}
