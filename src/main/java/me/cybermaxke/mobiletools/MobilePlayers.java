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

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class MobilePlayers {
	public static Map<String, MobilePlayer> players = new HashMap<String, MobilePlayer>();

	public static MobilePlayer getPlayer(Player player) {
		if (players.containsKey(player.getName())) {
			return players.get(player.getName());
		}

		MobilePlayer p = new MobilePlayer(player);
		players.put(player.getName(), p);
		return p;
	}

	public static boolean removePlayer(Player player) {
		if (!players.containsKey(player.getName())) {
			return false;
		}

		players.get(player.getName()).remove();
		players.remove(player.getName());
		return true;
	}
}