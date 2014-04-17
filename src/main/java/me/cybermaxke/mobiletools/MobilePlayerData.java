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

import org.bukkit.inventory.Inventory;

import me.cybermaxke.mobiletools.utils.NbtUtils;

import net.minecraft.server.v1_7_R3.IInventory;
import net.minecraft.server.v1_7_R3.NBTTagCompound;

public class MobilePlayerData {
	private final File folder;
	private final File file;
	private NBTTagCompound tag;

	public MobilePlayerData(MobileTools plugin, String name) {
		this.folder = plugin.getPlayerData();
		this.file = new File(this.folder, name + ".data");

		if (!this.file.exists()) {
			this.tag = new NBTTagCompound();
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