package me.Report.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class SpigotMessage implements Listener{
	
	public static void sendMessage(String message, ServerInfo server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		
		try {
			out.writeUTF(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		server.sendData("Report", b.toByteArray());
		
	}
	@EventHandler
	public void onMessage(PluginMessageEvent e) {
		if(e.getTag() == "Report") {
			if(e.getSender() instanceof Server) {
				Server server = (Server) e.getSender();
				ByteArrayInputStream b = new ByteArrayInputStream(e.getData());
				DataInputStream in = new DataInputStream(b);
				
				try {
					String get = in.readUTF();
					
						sendMessage(BungeeCord.getInstance().getPlayer(get).getServer().getInfo().getName(), server.getInfo());
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
	}

}
