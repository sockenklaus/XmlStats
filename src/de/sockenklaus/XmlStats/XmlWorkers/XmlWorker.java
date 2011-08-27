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

@SuppressWarnings("restriction")
public abstract class XmlWorker implements HttpHandler {
	
	public void handle(HttpExchange exchange) {
		Map<String, List<String>> parameters = new HashMap<String, List<String>>();
		
		if("get".equalsIgnoreCase(exchange.getRequestMethod())){
			String queryString = exchange.getRequestURI().getRawQuery();
			String xmlResponse = "";
			byte[] byteResponse;
			
			try {
				
				parameters = parseParameters(queryString);
				
			} catch(UnsupportedEncodingException ex){
				XmlStats.LogError("Fehler beim Parsen des HTTP-Query-Strings.");
				XmlStats.LogError(ex.getMessage());
			}
			
			xmlResponse = getXML(parameters);
			
			byteResponse = xmlResponse.getBytes();
			
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
	
	abstract String getXML(Map<String, List<String>> parameters);
}
