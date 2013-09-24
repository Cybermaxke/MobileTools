/**
 * 
 * This software is part of the MobileTools
 * 
 * MobileTools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 * 
 * MobileTools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MobileTools. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.mobiletools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.cybermaxke.mobiletools.utils.ConfigUtils;
import me.cybermaxke.mobiletools.utils.RandomValue;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class MobileConfiguration {
	private final Map<String, Object> defaults = new HashMap<String, Object>();

	private final File folder;
	private final File file;
	private YamlConfiguration config;

	public MobileConfiguration(MobileTools plugin) {
		this.folder = plugin.getDataFolder();
		this.file = new File(this.folder, "Config.yml");

		/**
		 * Command permissions.
		 */
		this.addDefault("cmd.perm", new Permission("mobiletools.cmd", PermissionDefault.OP));
		this.addDefault("craft.cmd.perm", new Permission("mobiletools.craft.cmd", PermissionDefault.OP));
		this.addDefault("chest.cmd.perm", new Permission("mobiletools.chest.cmd", PermissionDefault.OP));
		this.addDefault("workbench.cmd.perm", new Permission("mobiletools.workbench.cmd", PermissionDefault.OP));
		this.addDefault("furnace.cmd.perm", new Permission("mobiletools.furnace.cmd", PermissionDefault.OP));
		this.addDefault("anvil.cmd.perm", new Permission("mobiletools.anvil.cmd", PermissionDefault.OP));
		this.addDefault("brew.cmd.perm", new Permission("mobiletools.brew.cmd", PermissionDefault.OP));
		this.addDefault("enchant.cmd.perm", new Permission("mobiletools.enchant.cmd", PermissionDefault.OP));
		this.addDefault("ender.cmd.perm", new Permission("mobiletools.ender.cmd", PermissionDefault.OP));

		/**
		 * Enchanting table levels.
		 */
		this.addDefault("enchant.levels.line1", new RandomValue(6, 10));
		this.addDefault("enchant.levels.line2", new RandomValue(17, 22));
		this.addDefault("enchant.levels.line3", new RandomValue(27, 30));
	}

	/**
	 * Adds a new default path.
	 * @param path
	 * @param def
	 */
	public void addDefault(String path, Object def) {
		this.defaults.put(path, def);
	}

	/**
	 * Saves the defaults and missing values.
	 */
	public void save() {
		if (!this.folder.exists()) {
			this.folder.mkdirs();
		}

		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.config = YamlConfiguration.loadConfiguration(this.file);
		for (Entry<String, Object> en : this.defaults.entrySet()) {
			if (!this.config.contains(en.getKey())) {
				ConfigUtils.setObject(this.config, en.getKey(), en.getValue());
			}
		}

		try {
			this.config.save(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the config file.
	 */
	public void load() {
		this.save();
	}

	/**
	 * Gets the config.
	 * @return config
	 */
	public YamlConfiguration getConfig() {
		return this.config;
	}

	/**
	 * Gets a permission from the config.
	 * @param path
	 * @return permission
	 */
	public Permission getPermission(String path) {
		return ConfigUtils.getPermission(this.config.getConfigurationSection(path));
	}

	/**
	 * Gets a random value from the config.
	 * @param path
	 * @return randomValue
	 */
	public RandomValue getRandom(String path) {
		return ConfigUtils.getRandom(this.config.getConfigurationSection(path));
	}
}