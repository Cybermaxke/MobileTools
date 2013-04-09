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