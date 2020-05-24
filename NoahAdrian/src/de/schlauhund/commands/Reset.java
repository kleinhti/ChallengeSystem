package de.schlauhund.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.schlauhund.config.Config;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Reset implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {

		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.isOp()) {
				if (args.length == 0) {
					TextComponent tc = new TextComponent();
					tc.setText("§aZum Bestätigen des Resets bitte hier klicken");
					tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							(new ComponentBuilder("§eBestätigen").create())));
					tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reset N903hn()=G3498g)(G?Bjb3Z("));
					p.spigot().sendMessage(tc);
				} else if (args.length == 1 & args[0].equals("N903hn()=G3498g)(G?Bjb3Z(")) {
					Config c = new Config();
					c.setReset(true);
					p.performCommand("stop");
				}
			}
		}
		return false;
	}

}
