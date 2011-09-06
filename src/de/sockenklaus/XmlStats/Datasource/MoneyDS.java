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
package de.sockenklaus.XmlStats.Datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.iConomy.iConomy;
import com.iConomy.system.Account;
import com.iConomy.system.Holdings;

import de.sockenklaus.XmlStats.XmlStats;
import de.sockenklaus.XmlStats.XmlStatsRegistry;

/**
 * The Class MoneyDS.
 */
public class MoneyDS extends Datasource {

	private iConomy iConomy;
	private ArrayList<String> allPlayers;
	
	public MoneyDS(){
		this.iConomy = (iConomy)XmlStatsRegistry.get("iconomy");
		this.allPlayers = fetchAllPlayers();
	}
	
	public HashMap<String, Double> getBalances(){
		HashMap<String, Double> result = new HashMap<String, Double>();
		
		for (String playerName : allPlayers){
			result.put(playerName, getBalance(playerName));
		}
		
		return result;
	}
	
	@SuppressWarnings("static-access")
	private Double getBalance(String playerName){
		Double result = 0.0;
		
		if (XmlStats.checkiConomy()){
			if(this.iConomy.hasAccount(playerName)){
				Account account = this.iConomy.getAccount(playerName);
				
				if (account != null){
					Holdings balance = account.getHoldings();
					result = balance.balance();
				}
				else XmlStats.LogWarn("The player \""+playerName+"\" has an account but it isn't valid. Bad data will return.");
			}
			else XmlStats.LogWarn("The player \""+playerName+"\" doesn't have a bank account and this action will return bad data");
		}
		else {
			XmlStats.LogError("Something went wrong! /money.xml shouldn't be enabled but it's datasource was called! This will return bad results.");
		}
		
		return result;
	}

	/**
	 * @param list
	 * @return
	 */
	public int getSum(List<String> list) {
		int result = 0;
		
		for(String playerName : list){
			result+=this.getBalance(playerName);
		}
		
		return result;
	}
}
