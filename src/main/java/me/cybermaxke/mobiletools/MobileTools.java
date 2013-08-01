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
import java.util.HashMap;
import java.util.Map;

import me.cybermaxke.mobiletools.utils.converter.AlphaChestConverter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MobileTools extends JavaPlugin implements Listener {
	private final Map<String, MobilePlayer> players = new HashMap<String, MobilePlayer>();
	private final Map<String, MobilePlayerData> data = new HashMap<String, MobilePlayerData>();

	private File playerData;

	@Override
	public void onEnable() {
		this.playerData = new File(this.getDataFolder() + File.separator + "PlayerData");

		new MobileToolsCommands(this);
		new MobilePlayerTask(this);
		new AlphaChestConverter(this);

		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			this.removePlayer(p);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		this.getPlayer(e.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		this.removePlayer(e.getPlayer());
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getPlayer() instanceof Player) {
			this.getPlayer((Player) e.getPlayer()).save();
		}
	}

	/**
	 * Gets the player data folder.
	 * @return folder
	 */
	public File getPlayerData() {
		return this.playerData;
	}

	/**
	 * Gets the mobile player data for the player.
	 * @param player
	 * @return data
	 */
	public MobilePlayerData getPlayerData(String player) {
		if (this.data.containsKey(player)) {
			return this.data.get(player);
		}

		MobilePlayerData data = new MobilePlayerData(this, player);
		this.data.put(player, data);
		return data;
	}

	/**
	 * Gets the mobile player for the player.
	 * @param player
	 * @return mobilePlayer
	 */
	public MobilePlayer getPlayer(Player player) {
		String name = player.getName();

		if (this.players.containsKey(name)) {
			return this.players.get(name);
		}

		MobilePlayer player1 = new MobilePlayer(this, player);
		this.players.put(name, player1);
		return player1;
	}

	/**
	 * Removes the player from the data map.
	 * @param player
	 * @return removed
	 */
	private boolean removePlayer(Player player) {
		String name = player.getName();

		if (!this.players.containsKey(name)) {
			return false;
		}

		this.players.get(name).remove();
		this.players.remove(name);
		return true;
	}
}