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

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class ConfigUtils {

	/**
	 * Sets a object to the condig file.
	 * @param config
	 * @param path
	 * @param object
	 */
	public static void setObject(Configuration config, String path, Object object) {
		if (object instanceof Permission) {
			setPermission(getOrCreateSection(config, path), (Permission) object);
		} else if (object instanceof RandomValue) {
			setRandom(getOrCreateSection(config, path), (RandomValue) object);
		} else {
			config.set(path, object);
		}
	}

	/**
	 * Gets a configuration section and creates one if it doesn't exist.
	 * @param config
	 * @param path
	 * @return section
	 */
	public static ConfigurationSection getOrCreateSection(Configuration config, String path) {
		if (!config.contains(path)) {
			return config.createSection(path);
		} else {
			return config.getConfigurationSection(path);
		}
	}

	/**
	 * Gets a permission from the configuration section.
	 * @param section
	 * @return permission
	 */
	public static Permission getPermission(ConfigurationSection section) {
		PermissionDefault de = getEnumValue(PermissionDefault.class, section.getString("default"));
		String perm = section.getString("permission");
		return new Permission(perm, de);
	}

	/**
	 * Sets the permission to the configuration section.
	 * @param section
	 * @param permission
	 */
	public static void setPermission(ConfigurationSection section, Permission permission) {
		section.set("default", permission.getDefault().toString());
		section.set("permission", permission.getName());
	}

	/**
	 * Gets a random value from the configuration section.
	 * @param section
	 * @return randomValue
	 */
	public static RandomValue getRandom(ConfigurationSection section) {
		return new RandomValue(section.getInt("min"), section.getInt("max"));
	}

	/**
	 * Sets a random value to the configuration section.
	 * @param section
	 * @param value
	 */
	public static void setRandom(ConfigurationSection section, RandomValue value) {
		section.set("min", value.getMin());
		section.set("max", value.getMax());
	}

	/**
	 * Gets a enum value from the enum class.
	 * @param clazz
	 * @param value
	 * @return enumValue
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Enum> T getEnumValue(Class<T> clazz, String value) {
		return (T) Enum.valueOf(clazz, value.toUpperCase());
	}
}