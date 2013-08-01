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
	private MobileTools plugin;
	private int i;

	public MobilePlayerTask(MobileTools plugin) {
		this.runTaskTimer(plugin, 0, 1);
		this.plugin = plugin;
	}

	@Override
	public void run() {
		++this.i;
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			MobilePlayer mp = this.plugin.getPlayer(p);
			mp.getBrewingStand().h();
			mp.getFurnace().h();
			if (this.i > 400) {
				mp.save();
			}
		}
		if (this.i > 400) {
			this.i = 0;
		}
	}
}