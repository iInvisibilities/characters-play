package me.invis.character.command;

import me.invis.character.Characters;
import me.invis.character.command.util.CommandUtil;
import me.invis.character.manager.character.CharactersManager;
import me.invis.character.manager.character.impl.Character;
import me.invis.character.manager.wardrobe.WardrobesManager;
import me.invis.character.manager.wardrobe.impl.Wardrobe;
import me.invis.character.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetCharacterAgeCommand implements CommandExecutor {

    WardrobesManager wardrobesManager = Characters.inst().getWardrobesManager();
    CharactersManager charactersManager = Characters.inst().getCharactersManager();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!commandSender.hasPermission("character.change.age")) return true;
        if(!CommandUtil.isPlayer(commandSender, strings)) return true;

        Player player = Bukkit.getPlayer(strings[0]);
        assert player != null;

        if(!wardrobesManager.hasWardrobe(player)) {
            commandSender.sendMessage(ChatColor.RED + "This player has no characters setup!");
            return true;
        }

        Wardrobe playerWardrobe = wardrobesManager.getPlayerWardrobe(player);
        Character[] characters = playerWardrobe.getContents();

        if(strings.length == 3) {
            if(!StringUtil.isInt(strings[1]) || !StringUtil.isInt(strings[2])) {
                commandSender.sendMessage(ChatColor.RED + "/setcharacterage <player> [character-id] [age]");
                return true;
            }
            int characterIndex = Integer.parseInt(strings[1]);
            int newAge = Integer.parseInt(strings[2]);
            boolean isCurrentlyInUse = charactersManager.getPlayerCharacter(player).equals(characters[characterIndex]);

            characters[characterIndex].setAge(newAge);
            playerWardrobe.updateContents(characters);
            wardrobesManager.setWardrobe(player, playerWardrobe);
            commandSender.sendMessage(ChatColor.GREEN + "Updated age to " + newAge + " successfully!");

            if(isCurrentlyInUse) {
                charactersManager.clearPlayerCharacter(player);
                player.sendMessage(
                        ChatColor.RED + "The current character you're playing got it's age changed",
                        ChatColor.RED + " please re-select it to continue using it!");
            }

        }
        else {
            for (int i = 0; i < characters.length; i++) {
                Character character = characters[i];
                commandSender.sendMessage(" " + i + ". " + character.getName() + " (age: " + character.getAge() + ")");
            }
        }

        return true;
    }

}
