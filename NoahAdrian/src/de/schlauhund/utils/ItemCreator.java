package de.schlauhund.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCreator {

	public ItemStack createItem(Material material, String displayname) { 
		ItemStack stack = new ItemStack(material);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayname);
		stack.setItemMeta(meta);
		return stack;
	}
}
