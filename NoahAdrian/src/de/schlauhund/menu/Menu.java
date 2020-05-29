package de.schlauhund.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import de.schlauhund.config.Config;
import de.schlauhund.utils.ItemCreator;

public class Menu implements CommandExecutor, Listener {

	ItemCreator ic = new ItemCreator();
	Config c = new Config();

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {

		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.isOp()) {

				Inventory inv = Bukkit.createInventory(null, 3 * 9, "§eMenü");

				inv.setItem(9 + 1, ic.createItem(Material.HEART_OF_THE_SEA, "§eLeben"));
				inv.setItem(9 + 2, ic.createItem(Material.DIAMOND_SWORD, "§eChallenges"));
				inv.setItem(9 + 3, ic.createItem(Material.COMMAND_BLOCK, "§eEinstellungen"));
				p.openInventory(inv);

			}
		}

		return false;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) throws EventException {
		if (e.getClickedInventory().getSize() == 3 * 9) {

			switch (e.getCurrentItem().getItemMeta().getDisplayName()) {

			case "§eLeben":
				e.setCancelled(true);
				e.getWhoClicked().openInventory(hearts());
				return;
			case "§eChallenges":
				e.setCancelled(true);
				e.getWhoClicked().openInventory(Challenges());
				return;
			case "§eEinstellungen":
				e.setCancelled(true);
				e.getWhoClicked().openInventory(Settings());
				return;
			// Beginn der Challenges
			case "§eNo-Sneak":
				e.setCancelled(true);
				if (c.getSneak()) {
					c.setSneak(false);
					Bukkit.broadcastMessage("§eNo-Sneak §2§ldeaktiviert");
				} else {
					c.setSneak(true);
					Bukkit.broadcastMessage("§eNo-Sneak §2§laktiviert");
				}
				close(e);
				return;
			case "§eAll-Damage":
				e.setCancelled(true);
				if (c.getDamageAll()) {
					c.setDamageAll(false);
					Bukkit.broadcastMessage("§eDamage-All §2§ldeaktiviert");
				} else {
					c.setDamageAll(true);
					Bukkit.broadcastMessage("§eDamage-All §2§laktiviert");
				}
				close(e);
				return;
			case "§eBegrenzte Inventar Slots":
				e.setCancelled(true);
				e.getWhoClicked().openInventory(blockInventorySlots());
				return;
			case "§eNo-Regeneration":
				e.setCancelled(true);
				if (c.getRegeneration()) {
					c.setRegeneration(false);
					Bukkit.broadcastMessage("§eRegeneration §2§laktiviert");
				} else {
					c.setRegeneration(true);
					Bukkit.broadcastMessage("§eRegeneration §2§ldeaktiviert");
				}
				close(e);
				return;
			case "§eDouble Damage":
				e.setCancelled(true);
				if (c.getDoubleDamage()) {
					c.setDoubleDamage(false);
					Bukkit.broadcastMessage("§eDouble-Damage §2§ldeaktiviert");
				} else {
					c.setDoubleDamage(true);
					Bukkit.broadcastMessage("§eDouble-Damage §2§laktiviert");
				}
				close(e);
				return;
			case "§eNo Mob-Drop":
				e.setCancelled(true);
				if (c.getNoDrops()) {
					c.setNoDrops(false);
					Bukkit.broadcastMessage("§eMob-Drop §2§laktiviert");
				} else {
					c.setNoDrops(true);
					Bukkit.broadcastMessage("§eMob-Drop §2§ldeaktiviert");
				}
				close(e);
				return;
			case "§eReset on Death":
				e.setCancelled(true);
				if (c.getResetonDeath()) {
					c.setResetonDeath(false);
					Bukkit.broadcastMessage("§eReset on Death §2§ldeaktiviert");
				} else {
					c.setResetonDeath(true);
					Bukkit.broadcastMessage("§eReset on Death §2§laktiviert");
				}
				close(e);
				return;

			// Beginn der Settings
			case "§ePVP":
				e.setCancelled(true);
				if (c.getdisablePvP()) {
					c.setdisablePvP(false);
					Bukkit.broadcastMessage("§ePvP §2§laktiviert");
				} else {
					c.setdisablePvP(true);
					Bukkit.broadcastMessage("§ePvP §2§ldeaktiviert");
				}
				close(e);
				return;
			}
		} else if (e.getClickedInventory().getSize() == 6 * 9) {
			for (double i = 0.5; i < 10.5; i = i + 0.5) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§c" + i + " Herzen")) {
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.setHealthScale(i * 2);
						all.setMaxHealth(i * 2);
						all.setHealth(i * 2);
						all.setHealthScaled(true);
						all.sendMessage("§aDie maximalen Herzen wurden auf §c" + i + "§a gesetzt");
					}
					c.setHearts(i * 2);
					e.setCancelled(true);
					close(e);
					return;
				}
			}
		} else if (e.getClickedInventory().getSize() == 4 * 9) {
			for (int i = 0; i < 28; i++) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§2" + i + "§e Slots blockiert")) {
					for (int b = 8; b < 8 + c.getBlockedInventorySlots() + 1; b++) {
						for (Player all : Bukkit.getOnlinePlayers()) {
							all.getInventory().clear(b);
						}
					}
					c.setBlockedInventoryslots(i);
					setInventoryBlockers(i);
					close(e);
					return;
				}
			}
		} else {
			if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§cSlot blockiert")) {
				e.setCancelled(true);
			}
		}
	}

	private Inventory hearts() {
		Inventory inv = Bukkit.createInventory(null, 6 * 9, "§eHerzen");
		int b = 18;
		for (double i = 0.5; i < 10.5; i = i + 0.5) {
			inv.setItem(b, ic.createItem(Material.RED_DYE, "§c" + i + " Herzen"));
			b++;
		}
		return inv;
	}

	private Inventory Challenges() {
		Inventory inv = Bukkit.createInventory(null, 3 * 9, "§eChallenges");

		inv.setItem(9 + 1, ic.createItem(Material.PAPER, "§eNo-Sneak"));
		inv.setItem(9 + 2, ic.createItem(Material.RED_DYE, "§eAll-Damage"));
		inv.setItem(9 + 3, ic.createItem(Material.PAPER, "§eBegrenzte Inventar Slots"));
		inv.setItem(9 + 4, ic.createItem(Material.GOLDEN_APPLE, "§eNo-Regeneration"));
		inv.setItem(9 + 5, ic.createItem(Material.WITHER_SKELETON_SKULL, "§eDouble Damage"));
		inv.setItem(9 + 6, ic.createItem(Material.IRON_INGOT, "§eNo Mob-Drop"));
		inv.setItem(9 + 7, ic.createItem(Material.BARRIER, "§eReset on Death"));
		return inv;
	}

	private Inventory Settings() {
		Inventory inv = Bukkit.createInventory(null, 3 * 9, "§eEinstellungen");
		inv.setItem(9 + 1, ic.createItem(Material.DIAMOND_SWORD, "§ePVP"));
		return inv;
	}

	private Inventory blockInventorySlots() {
		Inventory inv = Bukkit.createInventory(null, 4 * 9, "§eBegrenzte Inventar Slots");
		for (int i = 0; i < 28; i++) {
			inv.setItem(i, ic.createItem(Material.PAPER, "§2" + i + "§e Slots blockiert"));
		}
		return inv;
	}

	public void setInventoryBlockers(int block) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			for (int i = 9; i < block + 9; i++) {
				all.getInventory().setItem(i, ic.createItem(Material.BARRIER, "§cSlot blockiert"));
			}
		}
	}

	// Funktion zum Schließen des Inventars
	private void close(InventoryClickEvent e) {
		e.getWhoClicked().closeInventory();
	}
}
