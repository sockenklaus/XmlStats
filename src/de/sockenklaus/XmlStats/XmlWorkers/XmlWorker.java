package de.sockenklaus.XmlStats.XmlWorkers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import de.sockenklaus.XmlStats.XmlStats;


public abstract class XmlWorker implements HttpHandler {
	
	public void handle(HttpExchange exchange) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		if("get".equalsIgnoreCase(exchange.getRequestMethod())){
			String queryString = exchange.getRequestURI().getRawQuery();
			String xmlResponse = "";
			byte[] byteResponse;
			
			try {
				
				parseQuery(queryString, parameters);
				
			} catch(UnsupportedEncodingException ex){
				XmlStats.LogError("Fehler beim Parsen des HTTP-Query-Strings.");
				XmlStats.LogError(ex.getMessage());
			}
			
			xmlResponse = processQuery(parameters);
			
			byteResponse = xmlResponse.getBytes();
			
			try {
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, byteResponse.length);
		        exchange.getResponseBody().write(byteResponse);
			}
			catch(IOException ex){
				XmlStats.LogError("Fehler beim Senden HTTP-Antwort.");
				XmlStats.LogError(ex.getMessage());
			}
	        
	        exchange.close();
		}
	}
	
	@SuppressWarnings("unchecked") 	
	public void parseQuery(String queryString, Map<String, Object> parameters) throws UnsupportedEncodingException {
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
				
				if (parameters.containsKey(key)){
					Object obj = parameters.get(key);
					
					if(obj instanceof List<?>){
						List<String> values = (List<String>)obj;
						values.add(value);
					}
					else if (obj instanceof String){
						List<String> values = new ArrayList<String>();
						values.add((String)obj);
						values.add(value);
						parameters.put(key, values);
					}
				}
				else {
					parameters.put(key, value);
				}
			}
		}
	}
	
	abstract String processQuery(Map<String, Object> parameters);
}
