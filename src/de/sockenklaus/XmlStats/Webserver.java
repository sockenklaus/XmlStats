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
@SuppressWarnings("restriction")
public class Webserver {

	private InetSocketAddress address;
	private HttpServer server = null;
	
	/**
	 * Instantiates a new web server.
	 *
	 * @param port the port
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Webserver(int port) throws IOException {
		this.address = new InetSocketAddress(port);
		
		server = HttpServer.create(address, 0);
		
		server.createContext("/users.xml", new XmlWorkerUsers());
		server.createContext("/userstats.xml", new XmlWorkerUserstats());
		
		if (XmlStats.isiConomyHooked()){
			server.createContext("/money.xml", new XmlWorkerMoney());
			XmlStats.LogInfo("iConomy seems to be loaded correctly. Enabling /money.xml.");
		}
		else {
			XmlStats.LogWarn("iConomy not loaded correctly. Disabling /money.xml");
		}
		
		this.server.start();
	}
	
	/**
	 * Stop server.
	 */
	public void stopServer() {
		server.stop(0);
	}
	
	/**
	 * Server running.
	 *
	 * @return true, if successful
	 */
	public boolean isRunning(){
		return (this.server == null) ? false : true;
	}
}
