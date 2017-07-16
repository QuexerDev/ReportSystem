package me.Report.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Report.database.MySQL;
import me.Report.listeners.ItemInteract;
import me.Report.main.Main;

public class ReportAPI {

	public static boolean isReportet(String name) {
		String UUID = UUIDFetcher.getUUID(name).toString();
		
		ResultSet rs = MySQL.getResult("SELECT * FROM Report WHERE UUID='"+UUID+"'");
		
		try {
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}
	public static List<String> getReporter(String name) {
		String UUID = UUIDFetcher.getUUID(name).toString();
		ResultSet rs = MySQL.getResult("SELECT * FROM Report WHERE UUID='"+UUID+"'");
		List<String> names = new ArrayList<>();
		try {
			while(rs.next()) {
				String von = rs.getString("von");
				if(!names.contains(von)) {
					names.add(von);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
		
	}
	public static int getCount(String name) {
		return getReporter(name).size();
	}
	
	public static String getGrund(String name) {
		String UUID = UUIDFetcher.getUUID(name).toString();
		ResultSet rs = MySQL.getResult("SELECT * FROM Report WHERE UUID='"+UUID+"'");
		List<String> names = new ArrayList<>();
		try {
			if(rs.next()) {
				String von = rs.getString("Grund");
				return von;
			} else {
				return "§cNicht vorhanden§8!";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "§cNicht vorhanden§8!";
	}
	public static List<String> getReportetPlayers() {
		ResultSet rs = MySQL.getResult("SELECT * FROM Report");
		List<String> names = new ArrayList<>();
		try {
			while(rs.next()) {
				String von = rs.getString("UUID");
				if(!names.contains(von)) {
					names.add(UUIDFetcher.getName(UUID.fromString(rs.getString(von))));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
	}
	public static void deleteReport(String name) {
		String UUID = UUIDFetcher.getUUID(name).toString();
		MySQL.update("DELETE FROM Report WHERE UUID='"+UUID+"'");
	}
	@SuppressWarnings("deprecation")
	public static void acceptReport(String name, String von) {
		Player accepter = Bukkit.getPlayer(von);
		Player target = Bukkit.getPlayer(name);
		if(isReportet(name)) {
			accepter.sendMessage(Main.pf+"§7Du bearbeitest nun den Report von §c"+name+" §7auf dem Server §e"+target.getServer().getServerName());
			ItemInteract.connect(accepter, target.getServer().getServerName());
			
			for(String names : getReporter(name)) {
				if(Bukkit.getPlayer(names) != null) {
					Bukkit.getPlayer(names).sendMessage(Main.pf+"§7Dein Report an §c"+name+" §7wird nun bearbeitet§8.\n"
																		+ Main.pf+"§7Vielen Dank für deine Aufmerksamteit§8!");
				}
			}
			deleteReport(name);
		} else {
			accepter.sendMessage(Main.pf+"§7Dieser Report wird bereits bearbeitet§8!");
		}
	}
	
	
}
