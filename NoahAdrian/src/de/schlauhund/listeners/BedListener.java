package de.schlauhund.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class BedListener implements Listener {

	ArrayList<String> bed = new ArrayList<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBedEnter(PlayerBedEnterEvent e) {
		if (e.getPlayer().getWorld().getTime() < 1000 | e.getPlayer().getWorld().getTime() > 12000) {
			bed.add(e.getPlayer().getDisplayName());
			Bukkit.broadcastMessage("§e" + e.getPlayer().getDisplayName() + " §aliegt nun im Bett");
			String str = "";
			for (Player all : Bukkit.getOnlinePlayers()) {

				if (!bed.contains(all.getDisplayName())) {
					str += all.getDisplayName() + ", ";
					all.sendTitle("§4Lege Dich ins Bett", "");
				}

				if (bed.contains(all.getDisplayName())) {
					Bukkit.broadcastMessage("§aEs liegen alle im Bett");
				}
				if (bed.size() != Bukkit.getOnlinePlayers().size()) {
					Bukkit.broadcastMessage("§aNicht im Bett sind: §c" + str);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBedLeave(PlayerBedLeaveEvent e) {
		if ((e.getPlayer().getWorld().getTime() < 24000 & e.getPlayer().getWorld().getTime() != 0)
				| e.getPlayer().getWorld().getTime() > 12000) {
			bed.remove(e.getPlayer().getDisplayName());
			Bukkit.broadcastMessage("§e" + e.getPlayer().getDisplayName() + " §chat das Bett verlassen");
			e.getPlayer().sendTitle("§4Lege Dich ins Bett", "");
		}
	}
}
