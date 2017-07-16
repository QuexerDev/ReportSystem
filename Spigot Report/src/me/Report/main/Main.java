package me.Report.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.Report.database.MySQL;
import me.Report.listeners.ItemInteract;
import net.md_5.bungee.api.ChatColor;





public class Main extends JavaPlugin{
	public static File file = new File("plugins//ReportSystemByQuexer", "config.yml");
	public static File ordner = new File("plugins//ReportSystemByQuexer");
	public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	public static String pf;
	
	
	
	
	@Override
	public void onEnable() {
		if(!ordner.exists()) {

            ordner.mkdir();

    }
    	
    	
        if(!file.exists()) {
            try {
                file.createNewFile();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        



        
        
        loadConfig();

        

        MySQL.Connect();
        MySQL.CreateTable();
        
        Bukkit.getPluginManager().registerEvents(new ItemInteract(), this);
		
	}
	 public void loadConfig() {
	        if(getConfig().get("Settings.prefix") != null) {
	            pf = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Settings.prefix"));
	        } else {
	            getConfig().set("Settings.prefix", "&8[&bReport&8] ");
	        }

	        if(getConfig().get("MySQL.username") != null) {
	        	 MySQL.username = getConfig().getString("MySQL.username");
	             MySQL.password = getConfig().getString("MySQL.password");
	             MySQL.database = getConfig().getString("MySQL.database");
	             MySQL.host = getConfig().getString("MySQL.host");
	             MySQL.port = getConfig().getInt("MySQL.port");
	        } else {
	            getConfig().set("MySQL.username", "root");
	            getConfig().set("MySQL.password", "password");
	            getConfig().set("MySQL.database", "database");
	            getConfig().set("MySQL.host", "host");
	            getConfig().set("MySQL.port", 3306);
	        }

	        	saveConfig();




	    }
	   
	    
	
	
	
}
