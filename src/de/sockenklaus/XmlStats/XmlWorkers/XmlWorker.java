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
package de.sockenklaus.XmlStats.XmlWorkers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import de.sockenklaus.XmlStats.Settings;
import de.sockenklaus.XmlStats.XmlStats;
import de.sockenklaus.XmlStats.XmlStatsRegistry;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlWorker.
 */
@SuppressWarnings("restriction")
public abstract class XmlWorker implements HttpHandler {
	
	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	public void handle(HttpExchange exchange) {
		Map<String, List<String>> parameters = new HashMap<String, List<String>>();
		
		Headers headers = exchange.getRequestHeaders();
		Settings settingsTemp = (Settings)XmlStatsRegistry.get("settings");
		
		if("get".equalsIgnoreCase(exchange.getRequestMethod())){
			String queryString = exchange.getRequestURI().getRawQuery();
			String xmlResponse = "";
			byte[] byteResponse = null;
			
			try {
				
				parameters = parseParameters(queryString);
				
			} catch(UnsupportedEncodingException ex){
				XmlStats.LogError("Fehler beim Parsen des HTTP-Query-Strings.");
				XmlStats.LogError(ex.getMessage());
			}
			
			xmlResponse = getXML(parameters);
						
			/*
			 * Check if the clients sends the header "Accept-encoding", the option "gzip-enabled" is true and the clients supports gzip.
			 */
			if(headers.containsKey("Accept-encoding") && settingsTemp.getBoolean("options.gzip-enabled")){
				XmlStats.LogDebug("Compression seems to be accepted by the client and activated in this plugin.");
				List<String> header = headers.get("Accept-encoding");
				try {
					XmlStats.LogDebug("There are "+header.size()+" values in the header");
					XmlStats.LogDebug("Let's take a look at the headers values:");
				}
				catch (Exception e){
					XmlStats.LogError(e.getMessage());
					e.printStackTrace();
				}
				for(String val : header){
					XmlStats.LogDebug("Accept-encoding: "+val);
					if(val.toLowerCase().indexOf("gzip") > -1){
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						try {
							XmlStats.LogDebug("OK... let's try gzip compression...");
							XmlStats.LogDebug("Actual size of the xml file: "+xmlResponse.getBytes().length+"Bytes");
							GZIPOutputStream gzip = new GZIPOutputStream(out);
							gzip.write(xmlResponse.getBytes());
							gzip.close();
							byteResponse = out.toByteArray();
							XmlStats.LogDebug("Compressed size of the xml file: "+byteResponse.length+"Bytes");
							exchange.getResponseHeaders().add("Content-encoding", "gzip");
							
						} catch (Exception e) {
							XmlStats.LogError("GZIP-Compression failed! Falling back to non-compressed output.");
							XmlStats.LogError(e.getMessage());
							e.printStackTrace();
							byteResponse = xmlResponse.getBytes();
						}
					}
				}
				
			}
			if (byteResponse == null || byteResponse.length == 0) {
				XmlStats.LogDebug("Compression is not enabled or doesn't work properly. Fallback to uncompressed output.");
				byteResponse = xmlResponse.getBytes();
			}
						
			try {
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, byteResponse.length);
		        exchange.getResponseBody().write(byteResponse);
			}
			catch(IOException ex){
				XmlStats.LogError("Fehler beim Senden der HTTP-Antwort.");
				XmlStats.LogError(ex.getMessage());
			}
	        
	        exchange.close();
		}
	}
	
	/**
	 * Parses the parameters.
	 *
	 * @param queryString the query string
	 * @return the map
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public Map<String, List<String>> parseParameters(String queryString) throws UnsupportedEncodingException {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		
		if (queryString != null){
			String pairs[] = queryString.split("[&]");
			
			for (String pair : pairs){
				String param[] = pair.split("[=]");
				
				String key = null;
				String value = null;
				
				if(param.length > 0){
					key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
				}
				
				if(param.length > 1){
					value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
				}
				
				if (result.containsKey(key)){
					List<String> values = result.get(key);
					
					values.add(value);
				}
				else {
					List<String> values = new ArrayList<String>();
					values.add(value);
					
					result.put(key, values);
				}
			}
		}
		return result;
	}
	
	/**
	 * Gets the xML.
	 *
	 * @param parameters the parameters
	 * @return the xML
	 */
	abstract String getXML(Map<String, List<String>> parameters);
}
