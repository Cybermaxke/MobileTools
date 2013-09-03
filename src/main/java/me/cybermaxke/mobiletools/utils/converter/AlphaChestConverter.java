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
package me.cybermaxke.mobiletools.utils.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import me.cybermaxke.mobiletools.MobilePlayerData;
import me.cybermaxke.mobiletools.MobileTools;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AlphaChestConverter {
	private final String chestExtension = ".chest.yml";
	private final MobileTools plugin;
	private final File folder;

	public AlphaChestConverter(MobileTools plugin) {
		this.plugin = plugin;
		this.folder = new File(plugin.getDataFolder() + File.separator + "AlphaChests");
	}

	/**
	 * Creates the folder.
	 */
	public void createFolder() {
		if (!this.folder.exists()) {
			this.folder.mkdirs();
		}
	}

	/**
	 * Loads the inventories items.
	 * @param inventory
	 * @param file
	 * @throws InvalidConfigurationException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void loadInventory(Inventory inventory, File file) throws IOException,
			InvalidConfigurationException {
		YamlConfiguration yaml = new AlphaYamlConfiguration();
		yaml.load(file);

		int size = yaml.getInt("size", 6 * 9);

		ConfigurationSection items = yaml.getConfigurationSection("items");
		for (int slot = 0; slot < size; slot++) {
			if (slot < inventory.getSize()) {
				if (items.isItemStack(slot + "")) {
					ItemStack item = items.getItemStack(slot + "");
					inventory.setItem(slot, item);
				}
			}
		}
	}

	/**
	 * Converts all the inventories from AlphaChest for MobileTools.
	 * @param folder
	 * @throws InvalidConfigurationException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void loadInventories() throws IOException, InvalidConfigurationException {
		this.createFolder();

		for (File file : this.folder.listFiles()) {
			String name = file.getName();

			if (name.endsWith(this.chestExtension)) {
				String playerName = name.replace(this.chestExtension, "");
				MobilePlayerData data = this.plugin.getPlayerData(playerName);

				Inventory inventory = Bukkit.createInventory(null, 6 * 9);
				this.loadInventory(inventory, file);

				data.saveInventory("Chest", inventory);

				File file1 = new File(this.folder, file.getName() + ".converted");
				if (file1.exists()) {
					file1.delete();
				}

				file.renameTo(file1);
			}
		}
	}
}