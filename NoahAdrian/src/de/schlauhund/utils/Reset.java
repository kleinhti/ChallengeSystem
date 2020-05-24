package de.schlauhund.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;

public class Reset {

	public static void resetWorld() {
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.kickPlayer("§cDie Welt wird resettet");
		}
		Bukkit.unloadWorld("world", false);
		Bukkit.unloadWorld("the_nether", false);
		Bukkit.unloadWorld("the_end", false);
		try {
			FileUtils.deleteDirectory(new File("world"));
			FileUtils.deleteDirectory(new File("world_nether"));
			FileUtils.deleteDirectory(new File("world_the_end"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
