/*
 * Copyright (C) [2011]  [Pascal Koenig]
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

import java.util.HashMap;
import java.util.List;

import org.bukkit.plugin.Plugin;

import com.nijikokun.register.Register;
import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Method.MethodAccount;
import com.nijikokun.register.payment.Methods;

import de.sockenklaus.XmlStats.Util;
import de.sockenklaus.XmlStats.Webserver;
import de.sockenklaus.XmlStats.XmlStats;
import de.sockenklaus.XmlStats.Exceptions.XmlStatsException;

/**
 * The Class MoneyDS.
 */
public class RegisterDS extends Datasource {
	
	private static RegisterDS instance;
	private Plugin register;
	
	private RegisterDS(){
		super();
	}
	
	private void hookRegister(){
		Plugin registerTemp = this.xmlstats.getServer().getPluginManager().getPlugin("Register");
		Webserver webserver = Webserver.getInstance();
		
		this.register = registerTemp;
	    XmlStats.LogInfo("Hooked into Register");
	    webserver.startRegister();
	}
	
	public static RegisterDS getInstance(){
		if(instance == null){
			XmlStats.LogDebug("Theres no instance of RegisterDS");
			if(Util.checkRegister()){
				XmlStats.LogDebug("Seems like there's a working instancen of Register");
				instance = new RegisterDS();
				instance.hookRegister();
			}
			else {
				XmlStats.LogWarn("Register or no payment method found. Will not hook into Register.");
			}
		}
		return instance;
		
	}
	

	
	public HashMap<String, Double> getBalances() throws XmlStatsException {
		HashMap<String, Double> result = new HashMap<String, Double>();
		
		for (String playerName : fetchAllPlayers()){
			result.put(playerName, getBalance(playerName));
		}
		
		return result;
	}
	
	public Double getBalance(String playerName) throws XmlStatsException {
		Double result = 0.0;
				
		if (Util.checkRegister()){
			
			Method paymentMethod = Methods.getMethod();
			
			if(paymentMethod.hasAccount(playerName)){
				MethodAccount account = paymentMethod.getAccount(playerName);
				
				if (account != null){
					result =  account.balance();
				}
				else throw new XmlStatsException("The player \""+playerName+"\" has an account but it isn't valid.");
			}
			else throw new XmlStatsException("The player \""+playerName+"\" doesn't have a bank account.");
		}
		else throw new XmlStatsException("Something went wrong! /user_balances.xml shouldn't be enabled but it's datasource was called!");
		
		return result;
	}

	/**
	 * @param list
	 * @return
	 */
	public int getSum(List<String> list) throws XmlStatsException {
		int result = 0;
		
		for(String playerName : list){
			result+=getBalance(playerName);
		}
		
		return result;
	}
	
	public Register getRegister(){
		return (Register)this.register;
	}
}
