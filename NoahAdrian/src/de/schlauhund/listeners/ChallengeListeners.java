package de.schlauhund.listeners;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;

import de.schlauhund.commands.Timer;
import de.schlauhund.config.Config;
import de.schlauhund.main.Main;
import de.schlauhund.menu.Menu;
import de.schlauhund.utils.ItemCreator;
import de.schlauhund.utils.Reset;
import net.minecraft.server.v1_15_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_15_R1.PacketPlayInClientCommand.EnumClientCommand;

public class ChallengeListeners implements Listener {

	Config c = new Config();
	ItemCreator ic = new ItemCreator();
	Menu m = new Menu();

	// No-Sneak Challenge
	@EventHandler
	public void onSneak(PlayerMoveEvent e) {
		if (c.getSneak() & e.getPlayer().isSneaking() & !e.getPlayer().isInsideVehicle() & !e.getPlayer().isSwimming()
				& e.getPlayer().isOnGround() & isPlaying(e.getPlayer())) {
			punishPlayer(e.getPlayer());
		}
	}

	// Damage-All Challenge
	// TODO:Die anderen dürfen keinen Schaden bekommen, wenn der Spieler blockt
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntityType().equals(EntityType.PLAYER) & !e.getCause().equals(DamageCause.CUSTOM)) {
			if (c.getDamageAll()) {
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (!all.equals(e.getEntity())) {
						all.damage(e.getDamage());
					}
				}
			}
			if (c.getDoubleDamage()) {
				e.setDamage(e.getDamage() * 2);
			}
		}
	}

	@EventHandler
	public void onDamagebyEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player & e.getDamager() instanceof Player & c.getdisablePvP()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDragonDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof EnderDragon) {
			Timer t = new Timer();
			LocalTime lt = LocalTime.ofSecondOfDay(t.getSeconds());
			DateTimeFormatter dt = DateTimeFormatter.ofPattern("HH:mm:ss");
			t.stopTimer();
			Bukkit.broadcastMessage(
					"§2Der EnderDragon wurde besiegt. Die Challenge wurde nach §e" + lt.format(dt) + "§2 beendet.");
		}
		if (c.getNoDrops() & !e.getEntityType().equals(EntityType.PLAYER)) {
			e.getDrops().clear();
		}
	}

	@EventHandler
	@SuppressWarnings("deprecation")
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage("§2[+] §e" + e.getPlayer().getName());
		for (int b = 8; b < 8 + c.getBlockedInventorySlots() + 1; b++) {
			for (Player all : Bukkit.getOnlinePlayers()) {
				if (isPlaying(all))
					all.getInventory().clear(b);
			}
			e.getPlayer().setMaxHealth(c.getHearts());
			e.getPlayer().setHealthScale(c.getHearts());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage("§c[-] §e" + e.getPlayer().getName());
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) throws InterruptedException {
		// m.setInventoryBlockers(c.getBlockedInventorySlots());
		if (isPlaying(e.getPlayer()))
			for (int i = 9; i < c.getBlockedInventorySlots() + 9; i++) {
				e.getPlayer().getInventory().setItem(i, ic.createItem(Material.BARRIER, "§cSlot blockiert"));
			}
		e.getPlayer().setMaxHealth(c.getHearts());
		e.getPlayer().setHealthScale(c.getHearts());
	}

	@EventHandler
	public void onRegenerate(EntityRegainHealthEvent e) {
		if (e.getEntityType().equals(EntityType.PLAYER) & c.getRegeneration()) {
			e.setCancelled(true);
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (e.getEntityType().equals(EntityType.PLAYER)) {
			if (e.getEntity().getLastDamageCause().equals(DamageCause.PROJECTILE)) {
				e.setDeathMessage(
						"§e" + e.getEntity().getDisplayName() + " §awar zu langsam um dem Pfeil auszuweichen");
			}
		}
		if (c.getResetonDeath()) {
			Reset.resetWorld();
			Bukkit.shutdown();
		}
//		respawn(e.getEntity());
	}

	@EventHandler
	public void onPing(ServerListPingEvent e) {
		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date resultdate = new Date(yourmilliseconds);
		e.setMotd("§aChallenge-Server\n" + "§e" + sdf.format(resultdate));
	}

	private void punishPlayer(Player p) {
		p.getWorld().strikeLightning(p.getLocation());
		p.getWorld().createExplosion(p.getLocation(), 10, true);
		p.damage(20);
	}

	@SuppressWarnings("unused")
	private void respawn(final Player player) {
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				((CraftPlayer) player).getHandle().playerConnection
						.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));

			}

		}, 1);
	}

	private Boolean isPlaying(Player p) {
		if (p.getGameMode().equals(GameMode.SPECTATOR) | p.getGameMode().equals(GameMode.CREATIVE))
			return false;
		return true;
	}
}
