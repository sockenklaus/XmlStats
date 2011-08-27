# XmlStats

This plugin for the Bukkit Minecraft Server API ([Homepage](http://bukkig.org) | [Github](https://github.com/Bukkit/Bukkit )) offers the possibility to access some playerstats via xml files. The provided data is basically generated by [Stats](https://github.com/nidefawl/Stats).

## Install

To install the plugin, simply place the compiled **XmlStats.jar** in your servers plugin directory and execute the /reload-command on your server. The plugin will create it's config file in the /plugins/XmlStats directory. To enable the webserver of the plugin you have to edit the config.yml, set the following value and reload the server again:
  
    options.webserver-enabled: true
    
## Usage

Afterwards you can access the following xml files:

For a list of all known players on your server:

    http://server:port/users.xml 
    
For the stats of all known players:
    
    http://server:port/userstats.xml
    
To fetch the stats of the given user:

    http://server:port/userstats.xml?player=username    
   
This will eventually be changed to a summarized view of all users stats as soon as the selection of users is implemented 
    
The following commands are planned but not implemented yet:


    
to get data provided by the iConomy plugin:
    
    http://server:port/money.xml
    http://server:port/money.xml?player=username