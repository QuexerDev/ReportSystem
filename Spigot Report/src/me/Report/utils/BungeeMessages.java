package me.Report.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.entity.Player;

import me.Report.main.Main;

public class BungeeMessages {
	
	public static void sendMessage(String message, Player p) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		
		try {
			out.writeUTF(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		p.sendPluginMessage(Main.getPlugin(Main.class), "Report", b.toByteArray());
	}

}
