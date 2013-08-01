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
package me.cybermaxke.mobiletools.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.minecraft.server.v1_6_R2.IInventory;
import net.minecraft.server.v1_6_R2.ItemStack;
import net.minecraft.server.v1_6_R2.NBTBase;
import net.minecraft.server.v1_6_R2.NBTTagCompound;
import net.minecraft.server.v1_6_R2.NBTTagList;

import org.bukkit.craftbukkit.v1_6_R2.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_6_R2.inventory.CraftItemStack;

import org.bukkit.inventory.Inventory;

public class NbtUtils {

	/**
	 * Saves the item to the tag.
	 * @param item
	 * @param tag
	 */
	public static void saveItem(org.bukkit.inventory.ItemStack item, NBTTagCompound tag) {
		CraftItemStack.asNMSCopy(item).save(tag);
	}

	/**
	 * Saves the inventories items to the tag.
	 * @param inventory
	 * @param tag
	 */
	public static NBTTagCompound saveInventory(Inventory inventory, NBTTagCompound tag) {
		return saveInventory(((CraftInventory) inventory).getInventory(), tag);
	}

	/**
	 * Saves the inventories items to the tag.
	 * @param inventory
	 * @param tag
	 */
	public static NBTTagCompound saveInventory(IInventory inventory, NBTTagCompound tag) {
		NBTTagList list = new NBTTagList();

		for (int slot = 0; slot < inventory.getSize(); slot++) {
			ItemStack item = inventory.getItem(slot);

			if (item != null) {
				NBTTagCompound tag2 = new NBTTagCompound();

				tag2.setByte("Slot", (byte) slot);
				item.save(tag2);

				list.add(tag2);
			}
		}

		tag.set("Items", list);
		return tag;
	}

	/**
	 * Saved the tag to the file.
	 * @param file
	 * @param tag
	 * @throws IOException
	 */
	public static void save(File file, NBTTagCompound tag) throws IOException {
		DataOutputStream dos =
				new DataOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
		NBTBase.a(tag, dos);
		dos.close();
	}

	/**
	 * Loads a item from the tag.
	 * @param tag
	 * @return item
	 */
	public static org.bukkit.inventory.ItemStack loadItem(NBTTagCompound tag) {
		return CraftItemStack.asCraftMirror(ItemStack.createStack(tag));
	}

	/**
	 * Loads items from the tag to the inventory.
	 * @param inventory
	 * @param tag
	 */
	public static void loadInventory(Inventory inventory, NBTTagCompound tag) {
		loadInventory(((CraftInventory) inventory).getInventory(), tag);
	}

	/**
	 * Loads items from the tag to the inventory.
	 * @param inventory
	 * @param tag
	 */
	public static void loadInventory(IInventory inventory, NBTTagCompound tag) {
		/**
		 * Loading the items from the new format.
		 */
		if (tag.hasKey("Items")) {
			NBTTagList list = tag.getList("Items");

			for (int i = 0; i < list.size(); i++) {
				NBTTagCompound tag2 = (NBTTagCompound) list.get(i);

				byte slot = tag2.getByte("Slot");
				if (slot < inventory.getSize()) {
					inventory.setItem(slot, ItemStack.createStack(tag2));
				}
			}
		/**
		 * Loading the items from the old format.
		 */
		} else {
			for (int slot = 0; slot < inventory.getSize(); slot++) {
				if (tag.hasKey("Slot" + slot)) {
					inventory.setItem(slot, ItemStack.createStack(tag.getCompound("Slot" + slot)));
				}
			}
		}
	}

	/**
	 * Loads a tag from the file.
	 * @param file
	 * @return tag
	 * @throws IOException
	 */
	public static NBTTagCompound load(File file) throws IOException {
		DataInputStream dis = getDataInput(file);
		NBTTagCompound tag = (NBTTagCompound) NBTBase.b(dis, 0);
		dis.close();
		return tag;
	}

	/**
	 * Gets the data input stream based on compression.
	 * @param file
	 * @return stream
	 * @throws IOException
	 */
	private static DataInputStream getDataInput(File file) throws IOException {
		if (isGzip(file)) {
			return new DataInputStream(new GZIPInputStream(new FileInputStream(file)));
		} else {
			return new DataInputStream(new FileInputStream(file));
		}
	}

	/**
	 * Gets if the file is compressed with gzip.
	 * @param file
	 * @return gzip
	 */
	private static boolean isGzip(File file) {
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			int magic = raf.read() & 0xff | (raf.read() << 8) & 0xff00;
			raf.close();
			return magic == GZIPInputStream.GZIP_MAGIC;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}