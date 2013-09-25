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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class MobileListener implements Listener {
	private final MobileConfiguration config;
	private final MobileTools plugin;

	public MobileListener(MobileTools plugin, MobileConfiguration config) {
		this.config = config;
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();
		MobilePlayer mp = this.plugin.getPlayer(player);

		if (player.hasPermission(this.config.getPermission("chest.loseondeath"))) {
			mp.getChest().clear();
		}

		if (player.hasPermission(this.config.getPermission("furnace.loseondeath"))) {
			mp.clearFurnace();
		}

		if (player.hasPermission(this.config.getPermission("brew.loseondeath"))) {
			mp.clearBrewingStand();
		}
	}
}