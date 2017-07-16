package me.Report.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
	public static String ItemName;
	public static int material;
	public static Integer slot;
	
	
	
	
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
        


        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        
        
        loadConfig();

        

        MySQL.Connect();
        MySQL.CreateTable();
        
        Bukkit.getPluginManager().registerEvents(new ItemInteract(), this);
		
	}
	 public void loadConfig() {
	        getConfig().options().copyDefaults(true);
	            getConfig().addDefault("Settings.prefix", "&8[&bReport&8] ");
	            getConfig().addDefault("Settings.ItemName", "&8<< &cReport &8>>");
	            getConfig().addDefault("Settings.ItemSlot", 4);
	            getConfig().addDefault("Settings.Material", 1);
	        

	            
	        	
	            getConfig().addDefault("MySQL.username", "root");
	            getConfig().addDefault("MySQL.password", "password");
	            getConfig().addDefault("MySQL.database", "database");
	            getConfig().addDefault("MySQL.host", "host");
	            getConfig().addDefault("MySQL.port", 3306);
	        

	        	saveConfig();
	        	
	        	 MySQL.username = getConfig().getString("MySQL.username");
	             MySQL.password = getConfig().getString("MySQL.password");
	             MySQL.database = getConfig().getString("MySQL.database");
	             MySQL.host = getConfig().getString("MySQL.host");
	             MySQL.port = getConfig().getInt("MySQL.port");
	        	 pf = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Settings.prefix"));
	        	 ItemName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Settings.ItemName"));
	        	 slot = getConfig().getInt("Settings.ItemSlot");
	        	 material = getConfig().getInt("Settings.Material");


	    }
	   
	    
	
	
	
}
