package com.example.hardcorethreelives;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final HardcoreThreeLives plugin;

    public JoinListener(HardcoreThreeLives plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Zorg dat de speler een entry heeft in data.yml
        plugin.getDataManager().ensurePlayerEntry(event.getPlayer());
    }
}
