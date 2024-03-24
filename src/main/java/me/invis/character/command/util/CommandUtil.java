package me.invis.character.command.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUtil {

    public static boolean isPlayer(CommandSender commandSender, String[] strings) {
        if(strings.length < 1) {
            commandSender.sendMessage(ChatColor.RED + "/character <player>");
            return false;
        }

        Player player = Bukkit.getPlayer(strings[0]);
        if(player == null || !player.isOnline()) {
            commandSender.sendMessage(ChatColor.RED + "Player not found!");
            return false;
        }

        return true;
    }

    public static String getStringFromArgs(String[] args) {
        return String.join(" ", args);
    }

}
