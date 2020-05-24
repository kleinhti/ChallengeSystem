package de.schlauhund.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Check implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {

		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.hasPermission("manager.check")) {
				if (args.length == 1) {
					if (args[0] != null) {
						Player t = Bukkit.getPlayer(args[0]);

						String ban = "";
						if (p.isBanned()) {
							ban = "Ja";
						}
						ban = "Nein";

						String op = "";
						if (p.isOp()) {
							op = "Ja";
						}
						TextComponent tc = new TextComponent();
						TextComponent ip = new TextComponent();
						TextComponent uuid = new TextComponent();
						op = "Nein";
						p.sendMessage("");
						p.sendMessage("§8 》§7 》 §aÜbersicht über §c§l" + t.getName() + "\n" + " \n");
						if (t.isOnline()) {
							p.sendMessage("§8 》§7 》 §aOnline\n");
						} else {
							p.sendMessage("§8 》§7 》 §cOffline\n");
						}
						p.sendMessage("\n");
						p.sendMessage(
								"§8 》§7 》 §aGamemode  §7>> §c" + t.getGameMode() + "\n" + "§8 》§7 》 §aOperator §7>> §c"
										+ op + "\n" + "§8 》§7 》 §aGebannt §7>> §c" + ban + "\n");
						uuid.setText("§8 》§7 》 §aUUID §7>> §c" + t.getUniqueId());
						uuid.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new ComponentBuilder("§eZum Kopieren hier klicken").create()));
						uuid.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://" + t.getUniqueId()));
						p.spigot().sendMessage(uuid);
						ip.setText("§8 》§7 》 §aIP-Adresse §7>> §c" + t.getAddress().toString().replaceAll("/", ""));
						ip.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								(new ComponentBuilder("§eZum Kopieren hier klicken").create())));
						ip.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
								"https://" + t.getAddress().toString().replaceAll("/", "")));
						p.spigot().sendMessage(ip);
						tc.setText("§8 》§7 》 §aPosition §7>> §c§lX §r§c" + (int) t.getLocation().getX()// Textinhalt
																										// wird gesetzt
								+ " §lY§r§c " + (int) t.getLocation().getY() + " §lZ§r§c "
								+ (int) t.getLocation().getZ());
						tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, // Texteigenschaften werden gesetzt
								(new ComponentBuilder("§eKlicke hier zum teleportieren")).create()));
						tc.setClickEvent(
								new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + t.getLocation().getBlockX() + " "
										+ t.getLocation().getBlockY() + " " + t.getLocation().getZ()));
						p.spigot().sendMessage(tc);// Text wird gesendet
						p.sendMessage("\n§8 》§7 》 §cPlugin coded by §4§lSchlauhund");

					}
				}
			}
		}

		return false;
	}

}
