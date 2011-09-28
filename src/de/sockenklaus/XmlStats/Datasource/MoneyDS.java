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

import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Method.MethodAccount;
import com.nijikokun.register.payment.Methods;

import de.sockenklaus.XmlStats.XmlStats;
import de.sockenklaus.XmlStats.XmlStatsRegistry;

/**
 * The Class MoneyDS.
 */
public class MoneyDS extends Datasource {

	private ArrayList<String> allPlayers;
	private XmlStats xmlstats;
	
	public MoneyDS(){
		this.allPlayers = fetchAllPlayers();
		this.xmlstats = (XmlStats)XmlStatsRegistry.get("xmlstats");
	}
	
	public HashMap<String, Double> getBalances(){
		HashMap<String, Double> result = new HashMap<String, Double>();
		
		for (String playerName : allPlayers){
			result.put(playerName, getBalance(playerName));
		}
		
		return result;
	}
	
	public Double getBalance(String playerName){
		Double result = 0.0;
				
		if (xmlstats.checkRegister()){
			
			Method paymentMethod = Methods.getMethod();
			
			if(paymentMethod.hasAccount(playerName)){
				MethodAccount account = paymentMethod.getAccount(playerName);
				
				if (account != null){
					result =  account.balance();
				}
				else XmlStats.LogWarn("The player \""+playerName+"\" has an account but it isn't valid. Bad data will return.");
			}
			else XmlStats.LogWarn("The player \""+playerName+"\" doesn't have a bank account and this action will return bad data");
		}
		else {
			XmlStats.LogError("Something went wrong! /user_balances.xml shouldn't be enabled but it's datasource was called! This will return bad results.");
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
