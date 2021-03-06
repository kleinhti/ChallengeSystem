package de.schlauhund.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.schlauhund.commands.Check;
import de.schlauhund.commands.Fly;
import de.schlauhund.commands.Force;
import de.schlauhund.commands.Heal;
import de.schlauhund.commands.Invsee;
import de.schlauhund.commands.Ping;
import de.schlauhund.commands.Position;
import de.schlauhund.commands.Summon;
import de.schlauhund.commands.Timer;
import de.schlauhund.commands.Vanish;
import de.schlauhund.config.Config;
import de.schlauhund.listeners.BedListener;
import de.schlauhund.listeners.ChallengeListeners;
import de.schlauhund.listeners.ChatFormat;
import de.schlauhund.menu.Menu;
import de.schlauhund.utils.Reset;

public class Main extends JavaPlugin {

	public static Main plugin;

	public void onEnable() {
		plugin = this;

		Bukkit.getConsoleSender().sendMessage("�aSystem started");
		Config c = new Config();
		c.setConfig();
		if (c.getReset()) {
			Bukkit.getConsoleSender().sendMessage("�cDie Welten werden resettet!");
			Reset.resetWorld();
			c.setReset(false);
		}

//		MLGChallenge.checkWorld();

		registerCommands();
		registerListeners();
	}

	private void registerCommands() {
		getCommand("timer").setExecutor(new Timer());
		getCommand("invsee").setExecutor(new Invsee());
		getCommand("vanish").setExecutor(new Vanish());
		getCommand("check").setExecutor(new Check());
		getCommand("settings").setExecutor(new Menu());
		getCommand("heal").setExecutor(new Heal());
		getCommand("pos").setExecutor(new Position());
		getCommand("reset").setExecutor(new de.schlauhund.commands.Reset());
		getCommand("ping").setExecutor(new Ping());
		getCommand("fly").setExecutor(new Fly());
		getCommand("summon").setExecutor(new Summon());
		getCommand("force").setExecutor(new Force());
	}

	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new Menu(), this);
		pm.registerEvents(new ChallengeListeners(), this);
		pm.registerEvents(new BedListener(), this);
		pm.registerEvents(new ChatFormat(), this);
		pm.registerEvents(new Force(), this);
	}

	public static Main getPlugin() {
		return plugin;
	}
}
