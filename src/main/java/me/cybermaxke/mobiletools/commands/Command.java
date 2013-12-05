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
package me.cybermaxke.mobiletools.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class Command extends org.bukkit.command.Command {
	private Permission permission;

	public Command(String name) {
		super(name);
	}

	/**
	 * Gets the actual permission that will be used.
	 * @return permission
	 */
	public Permission getCommandPermission() {
		return this.permission;
	}

	/**
	 * Sets the actual permission taht wll be used, and registering it for the defaults.
	 * @param permission
	 */
	public void setCommandPermission(Permission permission) {
		if (permission != null) {
			Bukkit.getPluginManager().addPermission(permission);
		}

		this.permission = permission;
		super.setPermission(permission == null ? null : permission.getName());
	}

	@Override
	public void setPermission(String permission) {
		super.setPermission(permission);

		this.permission = permission == null ? null :
			Bukkit.getPluginManager().getPermission(permission);
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		return false;
	}
}