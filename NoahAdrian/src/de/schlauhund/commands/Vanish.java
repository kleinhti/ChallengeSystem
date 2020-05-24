package de.schlauhund.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class Vanish implements CommandExecutor, Listener {
	
	public static ArrayList<Player> vanish = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {

		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.hasPermission("manager.vanish")) {
				if (args.length == 0) {
					if (vanish.contains(p)) {
						setVanish(p, false);
					} else {
						setVanish(p, true);
					}
//				} else if (args.length == 1) {
//					if (p.hasPermission("manager.vanish.other")) {
//						if (args[0] != null) {
//							Player t = Bukkit.getPlayer(args[0]);
//							if (Bukkit.getOnlinePlayers().contains(t)) {
//								if (vanish.contains(t)) {
//									for (Player all : Bukkit.getOnlinePlayers()) {
//										vanish.remove(t);
//										all.showPlayer(t);
//										t.sendMessage(prefix + "§c" + p.getName()
//												+ "§a hat deinen Vanish-Modus §cdeaktiviert§a!");
//										t.playSound(t.getLocation(), Sound.LEVEL_UP, 1, 1);
//										p.sendMessage(prefix + "§aDu hast den Vanish-Modus von §c" + t.getName()
//												+ "§a erfolgreich §cdeaktiviert§a!");
//									}
//
//								} else {
//									vanish.add(t);
//									for (Player all : Bukkit.getOnlinePlayers()) {
//										all.hidePlayer(t);
//										t.sendMessage(prefix + "§c" + p.getName()
//												+ "§a hat deinen Vanish-Modus §caktiviert§a!");
//										t.playSound(t.getLocation(), Sound.LEVEL_UP, 1, 1);
//										p.sendMessage(prefix + "§aDu hast den Vanish-Modus von §c" + t.getName()
//												+ "§a erfolgreich §caktiviert§a!");
//									}
//								}
//							} else
//								p.sendMessage(off);
//						} else
//							p.sendMessage(off);
//					} else
//						p.sendMessage(rights);
				} else
					p.sendMessage("§cDas hat nicht funktioniert! Bitte versuche §o/vanish");
			} 
		}

		return false;
	}

	@SuppressWarnings("rawtypes")
	public static ArrayList getVanish() {
		return vanish;

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (vanish.contains(p)) {
			vanish.remove(p);
		}

	}

	@SuppressWarnings("deprecation")
	public void setVanish(Player p, Boolean bool) {
		if (bool) {
			vanish.add(p);
			for (Player all : Bukkit.getOnlinePlayers()) {
				all.hidePlayer(p);
				p.sendMessage("§aVanish-Modus §a§laktiviert");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
			}
		} else {
			vanish.remove(p);
			for (Player all : Bukkit.getOnlinePlayers()) {
				all.showPlayer(p);
				p.sendMessage("§aVanish-Modus §c§ldeaktiviert");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
			}
		}
	}

}
