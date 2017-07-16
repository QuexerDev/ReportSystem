package me.Report.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.Report.commands.Command_Report;
import me.Report.database.MySQL;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin{

	public static String pf = "§8[§b§lReport§8] ";
	public static List<String> reasons = new ArrayList<>();
	 public static File file = new File("plugins//BanSystemByQuexer", "config.yml");
	    public static File ordner = new File("plugins//BanSystemByQuexer");
	    public static Configuration cfg;

	@Override
	public void onEnable() {
		BungeeCord.getInstance().getPluginManager().registerCommand(this, new Command_Report("report"));
		reasons.add("1");
		reasons.add("2");
		reasons.add("3");
		reasons.add("4");
		reasons.add("5");
		reasons.add("6");
		reasons.add("7");
		
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
        



        try {
            cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        loadConfig();

        

        MySQL.Connect();
        MySQL.CreateTable();

		
	}
	 public void loadConfig() {
	        if(getConfig().get("Settings.prefix") != null) {
	            pf = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Settings.prefix"));
	        } else {
	            getConfig().set("Settings.prefix", "&8[&cBanSystem&8] ");
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
	    public static void saveConfig(){
	        try {
	            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg,file);
	        }catch (IOException e) {
	            e.printStackTrace();
	        }

	    }
	    public static Configuration getConfig() {
	        return cfg;
	    }
	
}
