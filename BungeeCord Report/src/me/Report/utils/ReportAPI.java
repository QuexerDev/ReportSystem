package me.Report.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.Report.database.MySQL;
import me.Report.main.Main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
	@SuppressWarnings("deprecation")
	public static void ReportPlayer(String name, String von, String Grund) {
		ProxiedPlayer reporter = BungeeCord.getInstance().getPlayer(von);
		ProxiedPlayer target = BungeeCord.getInstance().getPlayer(name);
		if(target != null) {
		String UUID = UUIDFetcher.getUUID(name).toString();
		int Count = getCount(name)+1;
		
		MySQL.update("INSERT INTO Report(UUID, Grund, von) VALUES ('"+UUID+"','"+Grund+"','"+von+"')");
		
		reporter.sendMessage(Main.pf+"§7Du hast den Spieler §c"+name+" §7erfolgreich mit dem Grund§8: §e"+Grund+" §7gemeldet§8!");
		
		TextComponent text;
		if(Count <= 4) {
			text = new TextComponent(Main.pf+"§7Der Spieler §c"+name+" §7wurde mit dem Grund §e"+Grund+" §7gemeldet ");
			TextComponent extra = new TextComponent("§8[§aBearbeiten§8]");
			extra.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/report accept "+name));
			text.addExtra(extra);
		} else {
			text = new TextComponent(Main.pf+"§4§l"+name+" §4§lwurde sehr oft gemeldet ");
			TextComponent extra = new TextComponent("§8[§4Bearbeiten§8]");
			extra.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/report accept "+name));
			text.addExtra(extra);
		}
		
		for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
			if(all.hasPermission("report.accept")){
				all.sendMessage("§8§m-------------------------");
				all.sendMessage(text);
				all.sendMessage("§8§m-------------------------");
			}
			
			
		}
		
		} else {
		reporter.sendMessage(Main.pf+"§cDieser Spieler wurde nicht gemeldet§8!");
		}
		
		
		
	}
	public static void deleteReport(String name) {
		String UUID = UUIDFetcher.getUUID(name).toString();
		MySQL.update("DELETE FROM Report WHERE UUID='"+UUID+"'");
	}
	@SuppressWarnings("deprecation")
	public static void acceptReport(String name, String von) {
		ProxiedPlayer accepter = BungeeCord.getInstance().getPlayer(von);
		ProxiedPlayer target = BungeeCord.getInstance().getPlayer(name);
		if(isReportet(name)) {
			accepter.sendMessage(Main.pf+"§7Du bearbeitest nun den Report von §c"+name+" §7auf dem Server §e"+target.getServer().getInfo().getName());
			accepter.connect(target.getServer().getInfo());
			deleteReport(name);
			for(String names : getReporter(name)) {
				if(BungeeCord.getInstance().getPlayer(names) != null) {
					BungeeCord.getInstance().getPlayer(names).sendMessage(Main.pf+"§7Dein Report an §c"+name+" §7wird nun bearbeitet§8.\n"
																		+ Main.pf+"§7Vielen Dank für deine Aufmerksamteit§8!");
				}
			}
		} else {
			accepter.sendMessage(Main.pf+"§7Dieser Report wird bereits bearbeitet§8!");
		}
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
	
	

}
