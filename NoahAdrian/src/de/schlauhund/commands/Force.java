package de.schlauhund.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Force implements CommandExecutor, Listener {

	public static Player player;
	public static Player victim;

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {

		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.isOp()) {
				if (p.getName().equals("__Adri__")) {
					p.sendMessage("§cVergiss es!");
					return false;
				}
				if (player == null) {
					Player t = Bukkit.getPlayer(args[0]);
					player = p;
					victim = t;
					p.setGameMode(GameMode.CREATIVE);
					t.hidePlayer(p);
					p.hidePlayer(t);
					p.teleport(t);
				} else {
					player = null;
					victim = null;
					for (Player otherall : Bukkit.getOnlinePlayers()) {
						for (Player all : Bukkit.getOnlinePlayers()) {
							all.showPlayer(otherall);
						}
					}
				}

			}
		}
		return false;
	}
}
