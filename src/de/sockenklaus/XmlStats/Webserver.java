/*
 * Copyright (C) [2011]  [Pascal K�nig]
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

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import de.sockenklaus.XmlStats.XmlWorkers.*;

// TODO: Auto-generated Javadoc
/**
 * The Class WebServer.
 */
public class Webserver {

	private InetSocketAddress address;
	private HttpServer server = null;
	
	/**
	 * Instantiates a new web server.
	 *
	 * @param port the port
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Webserver() throws IOException {		
		Settings settingsTemp = (Settings)XmlStatsRegistry.get("settings");
		
		this.start(settingsTemp.getInt("options.webserver-port"));
	}
	
	/**
	 * Stop server.
	 */
	public void stop() {
		server.stop(0);
		this.server = null;
	}
	
	/**
	 * Server running.
	 *
	 * @return true, if successful
	 */
	public boolean isRunning(){
		return (this.server == null) ? false : true;
	}
	
	public void reload() throws IOException {
		this.stop();
		
		Settings settingsTemp = (Settings)XmlStatsRegistry.get("settings");
		
		this.start(settingsTemp.getInt("options.webserver-port"));
	}
	
	private void start(int port) throws IOException {
		this.address = null;
		this.server = null;
		
		this.address = new InetSocketAddress(port);
		
		this.server = HttpServer.create(this.address, 0);
		
		this.server.createContext("/users_list.xml", new XmlWorkerUsers());
		
		this.server.start();
	}
	
	protected void startiConomy(){
		if (this.isRunning() && XmlStats.checkiConomy()){
			server.createContext("/users_balances.xml", new XmlWorkerMoney());
			XmlStats.LogInfo("iConomy seems to be loaded correctly. Enabling /users_balances.xml");
		}
		else {
			XmlStats.LogWarn("iConomy or webserver not loaded correctly. Disabling /users_balances.xml");
		}
	}
	
	protected void startAchievements(){
		if(this.isRunning() && XmlStats.checkAchievements()){
			server.createContext("/user_achievements.xml", new XmlWorkerAchievements());
			XmlStats.LogInfo("Achievements seems to be loaded correctly. Enabling /user_achievements.xml");
		}
		else {
			XmlStats.LogWarn("Achievements or webserver not loaded correctly. Disabling /user_achievements.xml");
		}
	}
	
	protected void startStats(){
		if(this.isRunning() && XmlStats.checkStats()){
			server.createContext("/user_stats.xml", new XmlWorkerUserstats());
			XmlStats.LogInfo("Stats seems to be loaded correctly. Enabling /user_stats.xml");
		}
		else {
			XmlStats.LogWarn("Stats or webserver not loaded correctly. Disabling /user_stats.xml");
		}
	}
}
