package space.jaobernardi.tardis.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {
    public static void sendPrefixMsg(Player player, String msg) {
        // TODO: Implement config stuff here
        sendMsg(player, "&6&lTardis &8–&7 "+msg);
    }

    public static void sendMsg(Player player, String msg) {
        msg = ChatColor.translateAlternateColorCodes('&', msg);
        player.sendMessage(msg);
    }
}
