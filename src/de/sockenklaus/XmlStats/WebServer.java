package de.sockenklaus.XmlStats;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import de.sockenklaus.XmlStats.XmlWorkers.*;

public class WebServer {
	
	private InetSocketAddress address;
	private HttpServer server = null;
	
	
	/*
	 * Konstruktoren, um die Address zu initialisieren
	 */
	public WebServer(int port) throws IOException {
		this.address = new InetSocketAddress(port);
		
		server = HttpServer.create(address, 0);
		
		server.createContext("/users.xml", new XmlWorkerUsers());
		server.createContext("/userstats.xml", new XmlWorkerUserstats());
		server.createContext("/money.xml", new XmlWorkerMoney());
		
		this.server.start();
	}
	
	/*
	 * Beende den Server
	 */
	public void stopServer() {
		server.stop(0);
	}
	
	public boolean serverRunning(){
		return (this.server == null) ? false : true;
	}
}
