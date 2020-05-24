package de.schlauhund.commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.schlauhund.config.Config;
import de.schlauhund.main.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Timer implements CommandExecutor {

	Config c = new Config();

	private int taskID;
	private int seconds;

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String arg2, String[] args) {

		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.isOp()) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("start")) {
						if (Bukkit.getScheduler().isCurrentlyRunning(taskID)) {
							p.sendMessage("§cDer Timer läuft bereits");
							return false;
						}
						Bukkit.broadcastMessage("§aDer Timer wurde gestartet");
						seconds = 0;
						startTimer();

					} else if (args[0].equalsIgnoreCase("stop")) {
						Bukkit.getScheduler().cancelTask(taskID);
						Bukkit.broadcastMessage("§aDer Timer wurde gestoppt");
					} else if (args[0].equalsIgnoreCase("pause")) {
						Bukkit.getScheduler().cancelTask(taskID);
						c.setTimer(seconds);
						Bukkit.broadcastMessage("§aDer Timer wurde pausiert");
						LocalTime time = LocalTime.ofSecondOfDay(seconds);
						DateTimeFormatter form = DateTimeFormatter.ofPattern("HH:mm:ss");

						taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

							@Override
							public void run() {
								for (Player all : Bukkit.getOnlinePlayers()) {
									all.spigot().sendMessage(ChatMessageType.ACTION_BAR,
											TextComponent.fromLegacyText("§eTimer: §l" + time.format(form)));
								}
							}

						}, 0, 20);
					} else if (args[0].equalsIgnoreCase("resume")) {
						Bukkit.broadcastMessage("§aDer Timer wurde fortgesetzt");
						Bukkit.getScheduler().cancelTask(taskID);
						seconds = c.getTimer();
						c.setTimer(0);
						startTimer();
					}
				}
			}
		}
		return false;
	}

	public void startTimer() {
		// seconds = timeseconds;
		DateTimeFormatter form = DateTimeFormatter.ofPattern("HH:mm:ss");
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

				seconds++;
				LocalTime time = LocalTime.ofSecondOfDay(seconds);

				for (Player all : Bukkit.getOnlinePlayers()) {
					all.spigot().sendMessage(ChatMessageType.ACTION_BAR,
							TextComponent.fromLegacyText("§eTimer: §l" + time.format(form)));
				}
			}

		}, 0, 20);
	}

	public int getSeconds() {
		return seconds;
	}

	public void stopTimer() {
		Bukkit.getScheduler().cancelTask(taskID);
	}
}
