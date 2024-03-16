package me.invis.character.util;

import org.bukkit.ChatColor;

public class StringUtil {
    public static String[] splitPresenvingWords(String text, int length) {
        return text.replaceAll("(?:\\s*)(.{1,"+ length +"})(?:\\s+|\\s*$)", "$1\n").split("\n");
    }

    public static String cc(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }


    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
