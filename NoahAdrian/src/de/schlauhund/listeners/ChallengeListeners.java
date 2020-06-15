package de.schlauhund.listeners;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;

import de.schlauhund.commands.Timer;
import de.schlauhund.config.Config;
import de.schlauhund.main.Main;
import de.schlauhund.menu.Menu;
import de.schlauhund.utils.ItemCreator;
import de.schlauhund.utils.PlayerListPing;
import de.schlauhund.utils.Reset;
import de.schlauhund.utils.Targeter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_15_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_15_R1.PacketPlayInClientCommand.EnumClientCommand;

public class ChallengeListeners implements Listener {

	Config c = new Config();
	ItemCreator ic = new ItemCreator();
	Menu m = new Menu();

	// No-Sneak Challenge
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSneak(PlayerMoveEvent e) throws InterruptedException {
		if (c.getSneak() & e.getPlayer().isSneaking() & !e.getPlayer().isInsideVehicle() & !e.getPlayer().isSwimming()
				& e.getPlayer().isOnGround() & isPlaying(e.getPlayer())) {
			punishPlayer(e.getPlayer());
		}
//		if (e.getPlayer().getWorld().getName().equals("mlg") & e.getPlayer().isOnGround() & !e.getPlayer().isDead()
//				& e.getPlayer().getLocation().getBlock().getType().equals(Material.WATER)) {
//			e.getPlayer().getInventory().clear();
//			e.getPlayer().setInvulnerable(true);
//			e.getPlayer().teleport(MLGChallenge.location.get(e.getPlayer().getUniqueId()));
//			MLGChallenge.location.remove(e.getPlayer().getUniqueId());
//			e.getPlayer().setInvulnerable(false);
//			Inventory inv = MLGChallenge.inventory.get(e.getPlayer().getUniqueId());
//			for (int i = -106; i < 104; i++) {
//				e.getPlayer().getInventory().setItem(i, inv.getItem(i));
//			}
//			MLGChallenge.inventory.remove(e.getPlayer().getUniqueId());
//		}

		// Herzenanzeige
		if (!e.getPlayer().getNearbyEntities(5, 2, 5).isEmpty()) {
			Entity entity = Targeter.getTargetEntity(e.getPlayer());
			if (entity != null)
				e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
						TextComponent.fromLegacyText("§c§l" + ((LivingEntity) entity).getHealth() / 2 + "/"
								+ ((LivingEntity) entity).getMaxHealth() / 2 + "♥"));
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
		PlayerListPing.startPingList(e.getPlayer());
		if (c.getBlockedInventorySlots() != 0) {
			for (int b = 8; b < 8 + c.getBlockedInventorySlots() + 1; b++) {
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (isPlaying(all))
						all.getInventory().clear(b);
				}
				e.getPlayer().setMaxHealth(c.getHearts());
				e.getPlayer().setHealthScale(c.getHearts());
			}
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

	@EventHandler
	public void onDestroy(BlockBreakEvent e) {
		e.setDropItems(false);
		List<Material> blocks = new ArrayList<Material>();
		for (Material block : Material.values()) {
			if (block.isBlock()) {
				blocks.add(block);
			}
		}
		Material block = blocks.get(new Random().nextInt(blocks.size()));
		if (block.isItem())
			e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(block));
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onTarget(EntityTargetLivingEntityEvent e) {
		e.getEntity().sendMessage("t");
		if (e.getEntityType().equals(EntityType.PLAYER)) {
			Player p = (Player) e.getEntity();
			if (e.getTarget() instanceof LivingEntity) {
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						TextComponent.fromLegacyText("§c" + ((LivingEntity) e.getTarget()).getHealth() + "/"
								+ ((LivingEntity) e.getTarget()).getMaxHealth()));
			}
		}
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
