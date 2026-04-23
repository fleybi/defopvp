package dev.duel.plugin.arena;

import dev.duel.plugin.DuelPlugin;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ArenaManager {

    private final DuelPlugin plugin;
    private final Map<String, Arena> arenas = new LinkedHashMap<>();
    private File arenasFile;
    private FileConfiguration arenasConfig;

    public ArenaManager(DuelPlugin plugin) {
        this.plugin = plugin;
        loadArenas();
    }

    private void loadArenas() {
        arenasFile = new File(plugin.getDataFolder(), "arenas.yml");
        if (!arenasFile.exists()) {
            try { arenasFile.getParentFile().mkdirs(); arenasFile.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }
        arenasConfig = YamlConfiguration.loadConfiguration(arenasFile);

        ConfigurationSection section = arenasConfig.getConfigurationSection("arenas");
        if (section == null) return;

        for (String key : section.getKeys(false)) {
            Location s1 = deserializeLocation(section.getConfigurationSection(key + ".spawn1"));
            Location s2 = deserializeLocation(section.getConfigurationSection(key + ".spawn2"));
            if (s1 != null && s2 != null) {
                arenas.put(key, new Arena(key, s1, s2));
            }
        }
        plugin.getLogger().info("Loaded " + arenas.size() + " arenas.");
    }

    public void saveArenas() {
        for (Map.Entry<String, Arena> entry : arenas.entrySet()) {
            Arena a = entry.getValue();
            String path = "arenas." + entry.getKey();
            serializeLocation(arenasConfig, path + ".spawn1", a.getSpawn1());
            serializeLocation(arenasConfig, path + ".spawn2", a.getSpawn2());
        }
        try { arenasConfig.save(arenasFile); } catch (IOException e) { e.printStackTrace(); }
    }

    public void createArena(String name, Location spawn1, Location spawn2) {
        Arena arena = new Arena(name, spawn1, spawn2);
        arenas.put(name.toLowerCase(), arena);
        saveArenas();
    }

    public void deleteArena(String name) {
        arenas.remove(name.toLowerCase());
        arenasConfig.set("arenas." + name.toLowerCase(), null);
        try { arenasConfig.save(arenasFile); } catch (IOException e) { e.printStackTrace(); }
    }

    public Arena getAvailableArena() {
        for (Arena arena : arenas.values()) {
            if (!arena.isInUse() && arena.isValid()) {
                return arena;
            }
        }
        return null;
    }

    public void releaseArena(Arena arena) {
        if (arena != null) arena.setInUse(false);
    }

    public Map<String, Arena> getArenas() { return arenas; }

    public Arena getArena(String name) { return arenas.get(name.toLowerCase()); }

    private void serializeLocation(FileConfiguration config, String path, Location loc) {
        if (loc == null) return;
        config.set(path + ".world", loc.getWorld().getName());
        config.set(path + ".x", loc.getX());
        config.set(path + ".y", loc.getY());
        config.set(path + ".z", loc.getZ());
        config.set(path + ".yaw", loc.getYaw());
        config.set(path + ".pitch", loc.getPitch());
    }

    private Location deserializeLocation(ConfigurationSection sec) {
        if (sec == null) return null;
        String worldName = sec.getString("world");
        if (worldName == null) return null;
        org.bukkit.World world = plugin.getServer().getWorld(worldName);
        if (world == null) return null;
        double x = sec.getDouble("x");
        double y = sec.getDouble("y");
        double z = sec.getDouble("z");
        float yaw = (float) sec.getDouble("yaw");
        float pitch = (float) sec.getDouble("pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }
}
