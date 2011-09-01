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
		
		this.server.createContext("/users.xml", new XmlWorkerUsers());
		
		if(XmlStats.isStatsHooked()){
			server.createContext("/userstats.xml", new XmlWorkerUserstats());
			XmlStats.LogInfo("Stats seems to be loaded correctly. Enabling /userstats.xml");
		}
		
		if (XmlStats.isiConomyHooked()){
			server.createContext("/money.xml", new XmlWorkerMoney());
			XmlStats.LogInfo("iConomy seems to be loaded correctly. Enabling /money.xml.");
		}
		else {
			XmlStats.LogWarn("iConomy not loaded correctly. Disabling /money.xml");
		}
		
		this.server.start();
	}
}
