package de.schlauhund.utils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.schlauhund.main.Main;

public class PlayerListPing {

	@SuppressWarnings("unused")
	private static int taskID;

	public static void startPingList(Player p) {

		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				p.setPlayerListName(p.getName() + " §a" + ((CraftPlayer) p).getHandle().ping);

			}

		}, 0, 20);
	}
}
