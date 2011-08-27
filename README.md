# XmlStats

This plugin for the Bukkit Minecraft Server API ([Homepage](http://bukkig.org) | [Github](https://github.com/Bukkit/Bukkit )) offers the possibility to access some playerstats via xml files. The provided data is basically generated by [Stats](https://github.com/nidefawl/Stats).

## Usage

Simply place the compiled **XmlStats.jar** in your servers plugin directory and execute the /reload-command on your server. The plugin will create it's config file in the /plugins/XmlStats directory. To enable the webserver of the plugin you have to edit the XmlStats.conf, set the following value and reload the server again:
  
    xmlstats-enabled = true