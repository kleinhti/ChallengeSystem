package de.schlauhund.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import de.schlauhund.main.Main;

public class Config {

	FileConfiguration c = Main.getPlugin().getConfig();

	public Boolean getReset() {
		return c.getBoolean("server.reset");
	}

	public int getTimer() {
		return c.getInt("challenge.timer");
	}

	public double getHearts() {
		return c.getDouble("challenge.hearts");
	}

	public Boolean getSneak() {
		return c.getBoolean("challenges.sneak");
	}

	public Boolean getDamageAll() {
		return c.getBoolean("challenges.damageall");
	}

	public int getBlockedInventorySlots() {
		return c.getInt("challenges.blockedinventoryslots");
	}

	public Boolean getRegeneration() {
		return c.getBoolean("challenges.noregeneration");
	}

	public Boolean existsPosition(String name) {
		if (c.contains("position." + name)) {
			return true;
		}
		return false;
	}

	public Location getPosition(String position) {
		Location loc = new Location(null, c.getDouble("position." + position + ".x"),
				c.getDouble("position." + position + ".y"), c.getDouble("position." + position + ".z"));
		return loc;
	}

	@SuppressWarnings("rawtypes")
	public List getPositions() {
		return c.getList("positions");
	}

	public Boolean getDoubleDamage() {
		return c.getBoolean("challenges.doubledamage");
	}

	public Boolean getNoDrops() {
		return c.getBoolean("challenges.nodrop");
	}

	public void setTimer(int timeseconds) {
		c.set("challenge.timer", timeseconds);
		save();
	}

	public void setHearts(double hearts) {
		c.set("challenge.hearts", hearts);
		save();
	}

	public void setSneak(Boolean on) {
		c.set("challenges.sneak", on);
		save();
	}

	public void setDamageAll(Boolean on) {
		c.set("challenges.damageall", on);
		save();
	}

	public void setRegeneration(Boolean on) {
		c.set("challenges.noregeneration", on);
		save();
	}

	public void setBlockedInventoryslots(int slots) {
		c.set("challenges.blockedinventoryslots", slots);
		save();
	}

	public void setDoubleDamage(Boolean on) {
		c.set("challenges.doubledamage", on);
		save();
	}

	public void setNoDrops(Boolean on) {
		c.set("challenges.nodrop", on);
		save();
	}

	public void setReset(Boolean on) {
		c.set("server.reset", on);
		save();
	}

	@SuppressWarnings("unchecked")
	public void addPosition(String position, int x, int y, int z) {
		c.set("position." + position + ".x", x);
		c.set("position." + position + ".y", y);
		c.set("position." + position + ".z", z);
		@SuppressWarnings("rawtypes")
		List positions = c.getList("positions");
		positions.add(position);
		c.set("positions", positions);
		save();
	}

	@SuppressWarnings("unchecked")
	public void setConfig() {
		if (!c.contains("server.reset"))
			c.set("server.reset", false);
		if (!c.contains("challenge.timer"))
			c.set("challenge.timer", 0);
		if (!c.contains("challenge.hearts"))
			c.set("challenge.hearts", 10);
		if (!c.contains("challenges.sneak"))
			c.set("challenges.sneak", false);
		if (!c.contains("challenges.damageall"))
			c.set("challenges.damageall", false);
		if (!c.contains("challenges.blockedinventoryslots"))
			c.set("challenges.blockedinventoryslots", 0);
		if (!c.contains("positions")) {
			@SuppressWarnings("rawtypes")
			List list = new ArrayList();
			list.add("default");
			c.set("positions", list);
		}
		if (!c.contains("challenges.noregeneration"))
			c.set("challenges.noregeneration", false);
		if (!c.contains("challenges.doubledamage"))
			c.set("challenges.doubledamage", false);
		if (c.contains("challenges.nodrop")) {
			c.set("challenges.nodrop", false);
		}
		save();
	}

	private void save() {
		Main.getPlugin().saveConfig();
	}
}
