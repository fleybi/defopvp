package dev.duel.plugin.listeners;

import dev.duel.plugin.DuelPlugin;
import dev.duel.plugin.duel.Duel;
import dev.duel.plugin.kit.KitType;
import dev.duel.plugin.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DuelListener implements Listener {

    private final DuelPlugin plugin;

    public DuelListener(DuelPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        Duel duel = plugin.getDuelManager().getDuel(player.getUniqueId());
        if (duel == null) return;
        if (duel.getState() == Duel.State.STARTING) {
            event.setCancelled(true);
            return;
        }
        if (duel.getState() == Duel.State.ENDING) {
            event.setCancelled(true);
            return;
        }

        // Sumo: check if they fell off (void)
        if (duel.getKit() == KitType.SUMO) {
            if (event.getCause() == EntityDamageEvent.DamageCause.VOID || player.getLocation().getY() < 0) {
                event.setCancelled(true);
                handleDuelDeath(player, duel);
                return;
            }
        }

        // Near-death: prevent actual death, end duel
        if (player.getHealth() - event.getFinalDamage() <= 0) {
            event.setCancelled(true);
            handleDuelDeath(player, duel);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;
        if (!(event.getDamager() instanceof Player attacker)) return;

        Duel duel = plugin.getDuelManager().getDuel(victim.getUniqueId());
        if (duel == null) return;

        // Track hits
        duel.registerHit(attacker.getUniqueId());

        // Check if it's the opponent
        if (!duel.involves(attacker.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Duel duel = plugin.getDuelManager().getDuel(player.getUniqueId());
        if (duel != null) {
            event.setCancelled(true);
            player.setHealth(player.getMaxHealth());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Duel duel = plugin.getDuelManager().getDuel(player.getUniqueId());
        if (duel != null) {
            // Disconnecting = forfeit
            Player opponent = Bukkit.getPlayer(duel.getOpponent(player.getUniqueId()));
            if (opponent != null) {
                MessageUtil.send(opponent, "&e" + player.getName() + " &7disconnected. You win!");
                plugin.getDuelManager().endDuel(duel, opponent, player, "disconnect");
            } else {
                plugin.getArenaManager().releaseArena(duel.getArena());
            }
        }
        // Leave queue if in one
        if (plugin.getQueueManager().isInQueue(player.getUniqueId())) {
            plugin.getQueueManager().leaveQueue(player);
        }
    }

    private void handleDuelDeath(Player loser, Duel duel) {
        if (duel.getState() != Duel.State.ACTIVE) return;
        Player winner = Bukkit.getPlayer(duel.getOpponent(loser.getUniqueId()));
        if (winner == null) {
            plugin.getArenaManager().releaseArena(duel.getArena());
            return;
        }
        plugin.getDuelManager().endDuel(duel, winner, loser, "kill");
    }
}
