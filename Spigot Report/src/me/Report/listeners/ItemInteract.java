package me.Report.listeners;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.Report.main.Main;
import me.Report.utils.ReportAPI;
import me.Report.utils.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;

public class ItemInteract implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getTypeId() == Main.material
			   && p.getItemInHand().getItemMeta().getDisplayName() == Main.ItemName 
			   && p.getInventory().getItem(Main.slot).getTypeId() == Main.material) {
				
				Inventory inv = Bukkit.createInventory(null, 56, " §cReports");
				
				List<ItemStack> importantReports = new ArrayList<>();
				List<ItemStack> normalReports = new ArrayList<>();
				
				for (String name : ReportAPI.getReportetPlayers()) {
					
					if(ReportAPI.getCount(name) <= 4) {
						ItemStack is = new ItemStack(Material.SKULL_ITEM, ReportAPI.getCount(name));
						SkullMeta meta = (SkullMeta) is.getItemMeta();
						meta.setDisplayName("§a"+name+" §7| §aKlicke zum bearbeiten");
						List<String> lore = new ArrayList<>();
						lore.add("§7Grund§8: §e"+ReportAPI.getGrund(name));
						meta.setLore(lore);
						meta.setOwner(name);
						is.setItemMeta(meta);
						
						normalReports.add(is);
						
						
					} else {
						ItemStack is = new ItemStack(Material.SKULL_ITEM, ReportAPI.getCount(name));
						SkullMeta meta = (SkullMeta) is.getItemMeta();
						meta.setDisplayName("§c"+name+" §7| §aKlicke zum bearbeiten");
						List<String> lore = new ArrayList<>();
						lore.add("§7Grund§8: §e"+ReportAPI.getGrund(name));
						lore.add("§4§lWichtiger Report§8!");
						meta.setLore(lore);
						meta.setOwner(name);
						is.setItemMeta(meta);
						
						importantReports.add(is);
					}
					
				}
				for (int i = 0; i < inv.getSize(); i++) {
					
					for(ItemStack ir : importantReports) {
						inv.setItem(i, ir);
						
					}
					for(ItemStack nr : normalReports) {
						inv.setItem(i, nr);
						
					}
					
				}
				p.openInventory(inv);
				
				
			}
		}
	}
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getClickedInventory() != null) {
			if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
				if(e.getCurrentItem().getItemMeta().getLore() != null) {
					String[] itemname = e.getCurrentItem().getItemMeta().getDisplayName().split(" ");
					String name = ChatColor.stripColor(itemname[0]);
					ReportAPI.acceptReport(name, UUIDFetcher.getName(p.getUniqueId()));
				}
				
				
			}
		}
	}
	public static void connect(Player p, String Server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		
		try {
			out.writeUTF("connect");
			out.writeUTF(Server);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.sendPluginMessage(Main.getPlugin(Main.class), "BungeeCord", b.toByteArray());
		
		
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("report.accept")) {
			ItemStack is = new ItemStack(Main.material);
			ItemMeta meta = is.getItemMeta();
			meta.setDisplayName(Main.ItemName);
			List<String> lore = new ArrayList<>();
			lore.add("§7Rechtsklick um alle Reports zu sehen");
			meta.setLore(lore);
			
			is.setItemMeta(meta);
			
			p.getInventory().setItem(Main.slot, is);
		}
		
	}

}
