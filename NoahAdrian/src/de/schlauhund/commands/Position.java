package de.schlauhund.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.schlauhund.config.Config;

public class Position implements CommandExecutor {

	Config c = new Config();

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {

		if (s instanceof Player) {
			Player p = (Player) s;
			if (arg1.getName().equalsIgnoreCase("pos")) {
				if (args.length == 1) {
					String name = args[0];
					if (!c.existsPosition(name)) {
						c.addPosition(name, (int) p.getLocation().getX(), (int) p.getLocation().getY(),
								(int) p.getLocation().getZ());
						Bukkit.broadcastMessage("§2" + p.getDisplayName() + " §2§l" + name + "§r§c-> §eX:"
								+ (int) p.getLocation().getX() + " Y:" + (int) p.getLocation().getY() + " Z:"
								+ (int) p.getLocation().getZ());
					} else {
						Location loc = c.getPosition(name);
						int x = (int) loc.getX();
						int y = (int) loc.getY();
						int z = (int) loc.getZ();

						p.sendMessage("§2§l" + name + " §e-> §aX:" + x + " Y:" + y + " Z:" + z);
					}
				} else {
					for (int i = 0; i < c.getPositions().size(); i++) {
						p.sendMessage((String) c.getPositions().get(i));
					}
				}
			}
		}

		return false;
	}

}
