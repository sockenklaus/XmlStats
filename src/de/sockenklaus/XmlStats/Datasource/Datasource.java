package de.sockenklaus.XmlStats.Datasource;

import java.io.File;
import java.util.ArrayList;

public abstract class Datasource {
	
	
	
	protected ArrayList<String> fetchAllPlayers(){
		File[] files = new File("world/players").listFiles();
		ArrayList<String> result = new ArrayList<String>();
		
		for (int i = 0; i < files.length; i++){
			int whereDot = files[i].getName().lastIndexOf('.');
			
			if (0 < whereDot && whereDot <= files[i].getName().length() - 2){
				String playerName = files[i].getName().substring(0, whereDot);
				
				result.add(playerName);
			}
		}
		
		return result;
	}
}
