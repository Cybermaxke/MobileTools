package me.cybermaxke.mobiletools;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MobilePlayerTask extends BukkitRunnable {
	private int i;

	public MobilePlayerTask(Plugin plugin) {
		this.runTaskTimer(plugin, 0, 1);
	}

	@Override
	public void run() {
		++this.i;
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			MobilePlayer mp = MobilePlayers.getPlayer(p);
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