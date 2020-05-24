package de.schlauhund.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {

		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.isOp()) {
				if (args.length == 0) {
					p.sendMessage("§aDu wurdest erfolgreich geheilt!");
					p.setHealth(20);
					p.setFoodLevel(20);
				} else if (args.length == 1) {
					if (p.isOp()) {
						if (args[0] != null) {
							Player t = (Player) Bukkit.getPlayer(args[0]);
							t.sendMessage("§aDu wurdest von §c" + p.getName() + "§a geheilt!");
							p.sendMessage("§aDu hast den Spieler §c" + t.getName() + "§a geheilt!");
						}
					}
				} else
					p.sendMessage("§cDas hat nicht funktioniert! Bitte versuche §a/heal oder /heal §oName§r§c!");
			}
		}

		return false;
	}

}
