package dev.duel.plugin.listeners;

import dev.duel.plugin.DuelPlugin;
import dev.duel.plugin.duel.Duel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

    private final DuelPlugin plugin;

    public PlayerListener(DuelPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Player player)) return;
        Duel duel = plugin.getDuelManager().getDuel(player.getUniqueId());
        if (duel != null && duel.getState() == Duel.State.STARTING) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        // If somehow they respawn in a duel (shouldn't happen), clean up
        org.bukkit.entity.Player player = event.getPlayer();
        if (plugin.getDuelManager().isInDuel(player.getUniqueId())) {
            plugin.getDuelManager().getDuel(player.getUniqueId()).setState(Duel.State.ENDING);
        }
    }
}
