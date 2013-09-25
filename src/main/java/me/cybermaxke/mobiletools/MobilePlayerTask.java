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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MobilePlayerTask extends BukkitRunnable {
	private final MobileTools plugin;
	private final int updateDelay;

	private int updateTicks;

	public MobilePlayerTask(MobileTools plugin, int updateDelay) {
		this.runTaskTimer(plugin, updateDelay, updateDelay);

		this.plugin = plugin;
		this.updateDelay = updateDelay;
	}

	@Override
	public void run() {
		++this.updateTicks;

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			MobilePlayer mp = this.plugin.getPlayer(player);

			for (int i = 0; i < this.updateDelay; i++) {
				mp.getBrewingStand().h();
				mp.getFurnace().h();
			}

			if (this.updateTicks > (400 / this.updateDelay)) {
				mp.save();
			}
		}

		if (this.updateTicks > (400 / this.updateDelay)) {
			this.updateTicks = 0;
		}
	}
}