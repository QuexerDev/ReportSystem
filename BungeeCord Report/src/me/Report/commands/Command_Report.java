package me.Report.commands;

import me.Report.main.Main;
import me.Report.utils.ReportAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Command_Report extends Command {

	public Command_Report(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(CommandSender s, String[] args) {
		ProxiedPlayer p = (ProxiedPlayer)s;
		if(args.length == 0) {
			p.sendMessage(Main.pf+"§7Benutze§8: §c/report <Spieler> <Grund>"); 
			
		} else if(args.length == 2) {
			String name = args[0];
			String Grund = args[1];
				if(args[0].equalsIgnoreCase("accept")) {
					ReportAPI.acceptReport(name, p.getName());
				} else {
					
					if(Main.reasons.contains(Grund)){
						
						
						switch (Grund) {
						case "1":
							ReportAPI.ReportPlayer(name, p.getName(), "Hacking");
							break;
						case "2":
							ReportAPI.ReportPlayer(name, p.getName(), "Beleidigung");
							break;
						case "3":
							ReportAPI.ReportPlayer(name, p.getName(), "Skin");
							break;
						case "4":
							ReportAPI.ReportPlayer(name, p.getName(), "Name");
							break;
						case "5":
							ReportAPI.ReportPlayer(name, p.getName(), "Trolling");
							break;
						case "6":
							ReportAPI.ReportPlayer(name, p.getName(), "Spam");
							break;
						case "7":
							ReportAPI.ReportPlayer(name, p.getName(), "Werbung");
							break;
							

						
						}
						
						
						
					} else {
						p.sendMessage(Main.pf+"§7Diese Gründe kannst du verwenden§8:");
						p.sendMessage("§8§m-------------------------");
						p.sendMessage(Main.pf+"§eHacking §8- §c#1");
						p.sendMessage(Main.pf+"§eBeleidigung §8- §c#2");
						p.sendMessage(Main.pf+"§eSkin §8- §c#3");
						p.sendMessage(Main.pf+"§eName §8- §c#4");
						p.sendMessage(Main.pf+"§eTrolling §8- §c#5");
						p.sendMessage(Main.pf+"§eSpam §8- §c#6");
						p.sendMessage(Main.pf+"§eWerbung §8- §c#7");
						p.sendMessage("§8§m-------------------------");
						
					}
					
					
					
					
					
					
					
					
				}
			
			
			
			
			
			
		}

	}

}
