package me.cybermaxke.mobiletools;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MobileTools extends JavaPlugin implements Listener {
	private static MobileTools instance;

	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onEnable() {
		new MobileToolsCommands(this);
		new MobilePlayerTask(this);
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			MobilePlayers.removePlayer(p);
		}
	}

	public static MobileTools getInstance() {
		return instance;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		MobilePlayers.getPlayer(e.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		MobilePlayers.removePlayer(e.getPlayer());
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getPlayer() instanceof Player && MobilePlayers.getPlayer((Player) e.getPlayer()) != null) {
			MobilePlayers.getPlayer((Player) e.getPlayer()).save();
		}
	}
}