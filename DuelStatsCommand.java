package dev.duel.plugin.util;

import dev.duel.plugin.DuelPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtil {

    private static String prefix = "&8[&c&lDUEL&8] &r";

    public static void init(DuelPlugin plugin) {
        prefix = plugin.getConfig().getString("settings.prefix", "&8[&c&lDUEL&8] &r");
    }

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(colorize(prefix + message));
    }

    public static void sendRaw(CommandSender sender, String message) {
        sender.sendMessage(colorize(message));
    }

    public static void broadcast(Player p1, Player p2, String message) {
        Component comp = colorize(prefix + message);
        p1.sendMessage(comp);
        p2.sendMessage(comp);
    }

    public static Component colorize(String message) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    public static String getPrefix() { return prefix; }
}
