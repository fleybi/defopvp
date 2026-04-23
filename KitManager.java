package dev.duel.plugin.listeners;

import dev.duel.plugin.DuelPlugin;
import org.bukkit.event.Listener;

public class InventoryListener implements Listener {
    private final DuelPlugin plugin;
    public InventoryListener(DuelPlugin plugin) { this.plugin = plugin; }
}
