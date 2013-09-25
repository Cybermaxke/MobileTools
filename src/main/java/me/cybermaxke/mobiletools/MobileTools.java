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

import me.cybermaxke.mobiletools.utils.RandomValue;
import me.cybermaxke.mobiletools.utils.converter.AlphaChestConverter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

public class MobileTools extends JavaPlugin implements Listener {
	private final Map<String, MobilePlayer> players = new HashMap<String, MobilePlayer>();
	private final Map<String, MobilePlayerData> data = new HashMap<String, MobilePlayerData>();

	private File playerData;
	private MobileConfiguration config;
	private AlphaChestConverter alphaCoverter;

	@Override
	public void onEnable() {
		this.playerData = new File(this.getDataFolder() + File.separator + "PlayerData");

		this.alphaCoverter = new AlphaChestConverter(this);
		this.alphaCoverter.createFolder();

		this.config = new MobileConfiguration(this);

		/**
		 * Command permissions.
		 */
		this.config.addDefault("cmd.perm", new Permission("mobiletools.cmd", PermissionDefault.OP));
		this.config.addDefault("craft.cmd.perm", new Permission("mobiletools.craft.cmd", PermissionDefault.OP));
		this.config.addDefault("chest.cmd.perm", new Permission("mobiletools.chest.cmd", PermissionDefault.OP));
		this.config.addDefault("workbench.cmd.perm", new Permission("mobiletools.workbench.cmd", PermissionDefault.OP));
		this.config.addDefault("furnace.cmd.perm", new Permission("mobiletools.furnace.cmd", PermissionDefault.OP));
		this.config.addDefault("anvil.cmd.perm", new Permission("mobiletools.anvil.cmd", PermissionDefault.OP));
		this.config.addDefault("brew.cmd.perm", new Permission("mobiletools.brew.cmd", PermissionDefault.OP));
		this.config.addDefault("enchant.cmd.perm", new Permission("mobiletools.enchant.cmd", PermissionDefault.OP));
		this.config.addDefault("ender.cmd.perm", new Permission("mobiletools.ender.cmd", PermissionDefault.OP));

		/**
		 * Enchanting table levels.
		 */
		this.config.addDefault("enchant.levels.line1", new RandomValue(6, 10));
		this.config.addDefault("enchant.levels.line2", new RandomValue(17, 22));
		this.config.addDefault("enchant.levels.line3", new RandomValue(27, 30));

		/**
		 * Inventories to lose on death.
		 */
		this.config.addDefault("chest.loseondeath", new Permission("mobiletools.chest.loseondeath", PermissionDefault.FALSE));
		this.config.addDefault("brew.loseondeath", new Permission("mobiletools.brew.loseondeath", PermissionDefault.FALSE));
		this.config.addDefault("furnace.loseondeath", new Permission("mobiletools.furnace.loseondeath", PermissionDefault.FALSE));

		/**
		 * Delay for furnaces or brewing stand to tick.
		 */
		this.config.addDefault("updateticks", 3);
		this.config.load();

		new MobileToolsCommands(this, this.config);
		new MobileListener(this, this.config);
		new MobilePlayerTask(this, this.config.getConfig().getInt("updateticks"));

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
	 * Gets the configuration.
	 * @return config
	 */
	public MobileConfiguration getConfiguration() {
		return this.config;
	}

	/**
	 * Gets the player data folder.
	 * @return folder
	 */
	public File getPlayerData() {
		return this.playerData;
	}

	public AlphaChestConverter getAlphaChestConverter() {
		return this.alphaCoverter;
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

		this.players.remove(name).remove();
		return true;
	}
}