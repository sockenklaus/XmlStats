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
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import de.sockenklaus.XmlStats.XmlStats;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlWorker.
 */
public abstract class XmlWorker implements HttpHandler {
	protected DocumentBuilderFactory factory;
	protected DocumentBuilder builder;
	protected Document doc;
	protected DOMSource source;
	protected StringWriter writer;
	protected StreamResult result;
	protected TransformerFactory tf;
	protected Transformer transformer;
		
	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	public void handle(HttpExchange exchange) {
		Map<String, List<String>> parameters = new HashMap<String, List<String>>();
		
		Headers headers = exchange.getRequestHeaders();
		
		if("get".equalsIgnoreCase(exchange.getRequestMethod())){
			String queryString = exchange.getRequestURI().getRawQuery();
			String xmlResponse = "";
			byte[] byteResponse = null;
			
			/*
			 * Parse the parameters
			 */
			try {
				parameters = parseParameters(queryString);

				/*
				 * Create the XML doc stuff....
				 */
				this.factory = DocumentBuilderFactory.newInstance();
				this.builder = this.factory.newDocumentBuilder();
				this.doc = this.builder.newDocument();	
				this.source = new DOMSource(this.doc);
				this.writer = new StringWriter();
				this.result = new StreamResult(this.writer);
				this.tf = TransformerFactory.newInstance();
				this.transformer = this.tf.newTransformer();
				
				Element root = this.doc.createElement("xmlstats");
				this.doc.appendChild(root);
				/*
				 * Actually create the XML
				 */
				root.appendChild(getXML(parameters));

				/*
				 * Build string from XML
				 */
			}
			catch(TransformerConfigurationException e){
				
			}
			catch(ParserConfigurationException e){
				
			}
			catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				this.transformer.transform(this.source, this.result);

				xmlResponse = this.writer.toString(); 


				/*
				 * Check if the clients sends the header "Accept-encoding", the option "gzip-enabled" is true and the clients supports gzip.
				 */

				if(parameters.containsKey("gzip") && parameters.get("gzip").contains("true")){
					XmlStats.LogDebug("Raw gzip requested.");

					HttpContext context = exchange.getHttpContext();
					String filename = context.getPath().substring(1);

					byteResponse = compressData(xmlResponse.getBytes());
					exchange.getResponseHeaders().set("Content-type", "application/gzip");
					exchange.getResponseHeaders().set("Content-disposition", "attachment; filename="+filename+".gzip");
				}
				else if(clientAcceptsGzip(headers)) {
					byteResponse = compressData(xmlResponse.getBytes());
					exchange.getResponseHeaders().set("Content-encoding", "gzip");
				}	
				else {
					byteResponse = xmlResponse.getBytes();
				}


				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, byteResponse.length);
				exchange.getResponseBody().write(byteResponse);
			}
			
			catch(UnsupportedEncodingException e){
			}
			catch(IOException ex){
				XmlStats.LogError("Fehler beim Senden der HTTP-Antwort.");
				XmlStats.LogError(ex.getMessage());
			}
			catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	private Map<String, List<String>> parseParameters(String queryString) throws UnsupportedEncodingException {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		
		if (queryString != null){
			String pairs[] = queryString.split("[&]");
			
			for (String pair : pairs){
				String param[] = pair.split("[=]");
				
				String key = null;
				String[] valueArr = null;
				
				if(param.length > 0){
					key = URLDecoder.decode(param[0].toLowerCase(), System.getProperty("file.encoding"));
					valueArr = new String[1];
					valueArr[0] = "";
				}
				
				if(param.length > 1){
					valueArr = URLDecoder.decode(param[1].toLowerCase(), System.getProperty("file.encoding")).split(",");
				}
				
				List<String> values = new ArrayList<String>();
				for (String value : valueArr){
					if (!values.contains(value)){
						values.add(value);
					}
				}
					
				result.put(key, values);
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
	abstract Element getXML(Map<String, List<String>> parameters);
	
	private byte[] compressData(byte[] input){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] output;
		
		try {
			XmlStats.LogDebug("OK... let's try gzip compression...");
			XmlStats.LogDebug("Actual size of the xml file: "+input.length+" B");
			GZIPOutputStream gzip = new GZIPOutputStream(out);
			gzip.write(input);
			gzip.close();
			output = out.toByteArray();
			XmlStats.LogDebug("Compressed size of the xml file: "+output.length+" B");
		}
		catch(IOException e){
			XmlStats.LogError("GZIP-Compression failed! Returning empty byte[]");
			output = new byte[0];
		}
		
		return output;
	}

	private boolean clientAcceptsGzip(Headers headers){
		if(headers.containsKey("Accept-encoding")){
			List<String> header = headers.get("Accept-encoding");
			
			for(String val : header){
				if(val.toLowerCase().indexOf("gzip") > -1){
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	/**
	 * Gets the text elem.
	 *
	 * @param elemName the elem name
	 * @param text the text
	 * @return the text elem
	 */
	protected Element getTextElem(String elemName, String text){
		Element result = this.doc.createElement(elemName);
		result.setTextContent(text);
		return result;
	}
}
