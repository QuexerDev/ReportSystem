package me.Report.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class GetMessage implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String channel, Player p, byte[] bytes) {

		
		if(channel == "Report"){
			ByteArrayInputStream b = new ByteArrayInputStream(bytes);
			DataInputStream in = new DataInputStream(b);
			try {
				in.readUTF();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
		}
		
	}

}
