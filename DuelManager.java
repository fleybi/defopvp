package dev.duel.plugin.stats;

import dev.duel.plugin.DuelPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StatsManager {

    private final DuelPlugin plugin;
    private final Map<UUID, PlayerStats> statsCache = new HashMap<>();
    private File statsFile;
    private FileConfiguration statsConfig;

    public StatsManager(DuelPlugin plugin) {
        this.plugin = plugin;
        loadStats();
    }

    private void loadStats() {
        statsFile = new File(plugin.getDataFolder(), "stats.yml");
        if (!statsFile.exists()) {
            try { statsFile.getParentFile().mkdirs(); statsFile.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }
        statsConfig = YamlConfiguration.loadConfiguration(statsFile);
    }

    public PlayerStats getStats(UUID uuid) {
        return statsCache.computeIfAbsent(uuid, id -> {
            String path = "players." + id.toString();
            if (statsConfig.contains(path)) {
                return new PlayerStats(
                    id,
                    statsConfig.getInt(path + ".wins", 0),
                    statsConfig.getInt(path + ".losses", 0),
                    statsConfig.getInt(path + ".elo", getStartingElo()),
                    statsConfig.getInt(path + ".kills", 0),
                    statsConfig.getInt(path + ".deaths", 0)
                );
            }
            return new PlayerStats(id, getStartingElo());
        });
    }

    public int getElo(UUID uuid) {
        return getStats(uuid).getElo();
    }

    public void recordWin(UUID uuid, int eloGain) {
        getStats(uuid).addWin(eloGain);
        getStats(uuid).addKill();
        savePlayer(uuid);
    }

    public void recordLoss(UUID uuid, int eloLoss) {
        getStats(uuid).addLoss(eloLoss);
        getStats(uuid).addDeath();
        savePlayer(uuid);
    }

    private void savePlayer(UUID uuid) {
        PlayerStats stats = statsCache.get(uuid);
        if (stats == null) return;
        String path = "players." + uuid.toString();
        statsConfig.set(path + ".wins", stats.getWins());
        statsConfig.set(path + ".losses", stats.getLosses());
        statsConfig.set(path + ".elo", stats.getElo());
        statsConfig.set(path + ".kills", stats.getKills());
        statsConfig.set(path + ".deaths", stats.getDeaths());
        try { statsConfig.save(statsFile); } catch (IOException e) { e.printStackTrace(); }
    }

    public void saveAll() {
        for (UUID uuid : statsCache.keySet()) savePlayer(uuid);
    }

    public List<PlayerStats> getTopByElo(int limit) {
        List<PlayerStats> list = new ArrayList<>(statsCache.values());
        list.sort((a, b) -> Integer.compare(b.getElo(), a.getElo()));
        return list.subList(0, Math.min(limit, list.size()));
    }

    private int getStartingElo() {
        return plugin.getConfig().getInt("elo.starting-elo", 1000);
    }
}
