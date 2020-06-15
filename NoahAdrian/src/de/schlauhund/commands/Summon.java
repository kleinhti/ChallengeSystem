package de.schlauhund.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Summon implements CommandExecutor {

	private Player p;

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String arg2, String[] args) {

		if (s instanceof Player) {
			p = (Player) s;
			if (p.isOp()) {
				if (args.length == 2 && isEntity(args[0]) && Integer.parseInt(args[1]) < 1000) {
					spawn(EntityType.fromName(args[0].toUpperCase()), Integer.parseInt(args[1]));
				} else if (args.length == 1 & isEntity(args[0]))
					spawn(EntityType.fromName(args[0].toUpperCase()), 1);
			}
		}
		return false;
	}

	private void spawn(EntityType entity, int i) {
		for (int b = 0; b < i; b++) {
			p.getWorld().spawnEntity(p.getTargetBlock(null, 100).getLocation().add(0, 1, 0), entity);
		}
	}

	private Boolean isEntity(String name) {
		for (EntityType type : EntityType.values()) {
			if (type.name().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
}
