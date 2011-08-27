package de.sockenklaus.XmlStats.Datasource;

import java.util.ArrayList;

public class UsersDS extends Datasource {
	public ArrayList<String> getAllPlayers(){
		return fetchAllPlayers();
	}
}
