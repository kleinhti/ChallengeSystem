package de.schlauhund.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormat implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
//		Config c = new Config();
		// if (c.getChatMuted() == false) {

		e.setFormat("§a" + e.getPlayer().getName() + " §e>> §f" + e.getMessage());

		if (e.getPlayer().hasPermission("manager.writecolor")) {
			e.setFormat("§a" + e.getPlayer().getDisplayName() + " §e>> §f" + e.getMessage().replaceAll("&", "§"));
		}

//		} else {
//			e.getPlayer().sendMessage("§cDer Chat ist aktuell deaktiviert");
//			e.setCancelled(true);
	}
}
//}
