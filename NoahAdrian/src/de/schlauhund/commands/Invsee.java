package de.schlauhund.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Invsee implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {

		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.hasPermission("manager.invsee")) {
				if (args.length == 1) {
					if (args[0] != null) {
						Player t = Bukkit.getPlayer(args[0]);
						p.sendMessage("§aDu siehst nun das Inventar von §c" + t.getName() + "§a.");
						Inventory inv = t.getInventory();
						p.openInventory(inv);
						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
					} else
						p.sendMessage("§cDer angegebene Spieler ist offline");
				} else
					p.sendMessage("§cDas hat nicht funktioniert! Bitte versuche §a/invsee [NAME]§c!");
			} else
				p.sendMessage("§cDazu hast Du keine Recht");
		}

		return false;
	}

}
