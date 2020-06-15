package de.schlauhund.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Ping implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {

		if (s instanceof Player) {
			Player p = (Player) s;
			if (args.length == 0) {
				int ping = ((CraftPlayer) p).getHandle().ping;
				p.sendMessage("§ePing: §2" + ping + " ms");
//				MLGChallenge.startMLG(p);
			}
		}

		return false;
	}
}
