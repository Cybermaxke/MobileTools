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

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class MobileToolsCommands implements CommandExecutor {
	private MobileTools plugin;

	private Permission workbench = new Permission("mobiletools.workbench", PermissionDefault.OP);
	private Permission chest = new Permission("mobiletools.chest", PermissionDefault.OP);
	private Permission furnace = new Permission("mobiletools.furnace", PermissionDefault.OP);
	private Permission anvil = new Permission("mobiletools.anvil", PermissionDefault.OP);
	private Permission brewingstand = new Permission("mobiletools.brewingstand", PermissionDefault.OP);
	private Permission enchantingtable = new Permission("mobiletools.enchantingtable", PermissionDefault.OP);

	public MobileToolsCommands(MobileTools plugin) {
		this.plugin = plugin;

		plugin.getCommand("Chest").setExecutor(this);
		plugin.getCommand("Craft").setExecutor(this);
		plugin.getCommand("Furnace").setExecutor(this);
		plugin.getCommand("Anvil").setExecutor(this);
		plugin.getCommand("Brew").setExecutor(this);
		plugin.getCommand("Enchant").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You have to be a player to perform that command.");
			return true;
		}

		Player p = (Player) sender;
		MobilePlayer mp = this.plugin.getPlayer(p);
		if (label.equalsIgnoreCase("Chest")) {
			if (!p.hasPermission(this.chest)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to perform that command.");
				return true;
			}

			mp.updateChestSize();
			mp.openChest();
			return true;
		}

		if (label.equalsIgnoreCase("Craft")) {
			if (!p.hasPermission(this.workbench)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to perform that command.");
				return true;
			}

			mp.openWorkbench();
			return true;
		}

		if (label.equalsIgnoreCase("Furnace")) {
			if (!p.hasPermission(this.furnace)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to perform that command.");
				return true;
			}

			mp.openFurnace();
			return true;
		}

		if (label.equalsIgnoreCase("Anvil")) {
			if (!p.hasPermission(this.anvil)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to perform that command.");
				return true;
			}

			mp.openAnvil();
			return true;
		}

		if (label.equalsIgnoreCase("Brew")) {
			if (!p.hasPermission(this.brewingstand)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to perform that command.");
				return true;
			}

			mp.openBrewingStand();
			return true;
		}

		if (label.equalsIgnoreCase("Enchant")) {
			if (!p.hasPermission(this.enchantingtable)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to perform that command.");
				return true;
			}

			mp.openEnchantingTable();
			return true;
		}

		return false;
	}
}