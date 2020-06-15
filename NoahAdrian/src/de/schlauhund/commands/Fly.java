package de.schlauhund.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String arg2, String[] args) {

		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.isOp()) {
				if (cmd.getName().equalsIgnoreCase("fly")) {
					if (args.length == 0) {
						if (p.getAllowFlight()) {
							p.setAllowFlight(false);
							p.sendMessage("§aDu hast §eFly §l§cdeaktiviert");
						} else {
							p.setAllowFlight(true);
							p.sendMessage("§aDu hast §eFly §l§2aktiviert");
						}
					} else if (args.length == 1 & args[0] != null) {
						@SuppressWarnings("deprecation")
						Player t = (Player) Bukkit.getOfflinePlayer(args[0]);
						if (t.isOnline()) {
							if (t.getAllowFlight()) {
								t.setAllowFlight(false);
								t.sendMessage("§aFly wurde §c§ldeaktiviert");
								p.sendMessage("§aDu hast Fly für §e" + t.getName() + " §c§ldeaktiviert");
							} else {
								t.setAllowFlight(true);
								t.sendMessage("§aFly wurde §2§laktiviert");
								p.sendMessage("§aDu hast Fly für §e" + t.getName() + " §2§laktiviert");
							}
						}
					} else
						p.sendMessage("§cBitte nutze §a/fly §coder §a/fly §oName");
				}
			}
		}

		return false;
	}

}
