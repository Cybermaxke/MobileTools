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

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

public class CommandRegister {
	private final CommandMap map;

	public CommandRegister() {
		this.map = CommandRegister.getCommandMap();
	}

	public void register(Command command) {
		this.map.register("", command);
	}

	public static CommandMap getCommandMap() {
		Class<?> clazz = Bukkit.getServer().getClass();
		while (clazz != null) {
			for (Field field1 : clazz.getDeclaredFields()) {
				if (field1.getName().equals("commandMap")) {
					try {
						return (CommandMap) field1.get(Bukkit.getServer());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return null;
	}
}