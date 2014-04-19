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

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;

import me.cybermaxke.mobiletools.utils.NbtUtils;

import net.minecraft.server.v1_7_R3.IInventory;
import net.minecraft.server.v1_7_R3.NBTTagCompound;

public class MobilePlayerData {
	private File folder;
	private File file;
	private NBTTagCompound tag;

	public MobilePlayerData(MobileTools plugin, OfflinePlayer player) {
		this.folder = plugin.getPlayerData();
		/** Old data format. */
		File oldFile = new File(this.folder, player.getName() + ".data");
		/** New data format. */
		File newFile = new File(this.folder, player.getUniqueId().toString() + ".data");

		if (oldFile.exists()) {
			if (!newFile.exists()) {
				this.file = oldFile;
				this.load();
			}
			oldFile.delete();
		}

		this.file = newFile;
		if (!this.file.exists()) {
			if (this.tag == null) {
				this.tag = new NBTTagCompound();
			}
			this.save();
		} else {
			this.load();
		}
	}

	public NBTTagCompound getTag() {
		return this.tag;
	}

	public void save() {
		try {
			if (!this.folder.exists()) {
				this.folder.mkdirs();
			}

			if (!this.file.exists()) {
				this.file.createNewFile();
			}

			NbtUtils.save(this.file, this.tag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void load() {
		if (!this.file.exists()) {
			return;
		}

		try {
			this.tag = NbtUtils.load(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadInventory(String tag, Inventory inventory) {
		if (this.tag.hasKey(tag)) {
			NbtUtils.loadInventory(inventory, this.tag.getCompound(tag));
		}
	}

	public void loadInventory(String tag, IInventory inventory) {
		if (this.tag.hasKey(tag)) {
			NbtUtils.loadInventory(inventory, this.tag.getCompound(tag));
		}
	}

	public void saveInventory(String tag, Inventory inventory) {
		this.tag.set(tag, NbtUtils.saveInventory(inventory, new NBTTagCompound()));
	}

	public void saveInventory(String tag, IInventory inventory) {
		this.tag.set(tag, NbtUtils.saveInventory(inventory, new NBTTagCompound()));
	}
}