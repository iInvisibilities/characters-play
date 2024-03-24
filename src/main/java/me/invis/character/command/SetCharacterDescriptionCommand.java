package me.invis.character.command;

import me.invis.character.Characters;
import me.invis.character.command.util.CommandUtil;
import me.invis.character.manager.character.CharactersManager;
import me.invis.character.manager.character.impl.Character;
import me.invis.character.manager.wardrobe.WardrobesManager;
import me.invis.character.manager.wardrobe.impl.Wardrobe;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SetCharacterDescriptionCommand implements CommandExecutor {
    WardrobesManager wardrobesManager = Characters.inst().getWardrobesManager();
    CharactersManager charactersManager = Characters.inst().getCharactersManager();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!commandSender.hasPermission("character.change.description")) return true;
        if(!(commandSender instanceof Player)) return false;

        Player player = ((Player) commandSender);

        if(!wardrobesManager.hasWardrobe(player)) {
            commandSender.sendMessage(ChatColor.RED + "You didn't choose a character yet!");
            return true;
        }

        Wardrobe playerWardrobe = wardrobesManager.getPlayerWardrobe(player);
        Character currentCharacter = charactersManager.getPlayerCharacter(player);

        Character[] wardrobeContents = playerWardrobe.getContents();
        int index = -1;
        for (Character wardrobeChar : wardrobeContents) {
            if(currentCharacter.equals(wardrobeChar)) index = Arrays.asList(wardrobeContents).indexOf(wardrobeChar);
        }

        currentCharacter.setDescription(CommandUtil.getStringFromArgs(strings));
        wardrobeContents[index] = currentCharacter;

        // save the data
        charactersManager.applyCharacterToPlayer(currentCharacter, player);
        playerWardrobe.updateContents(wardrobeContents);
        wardrobesManager.setWardrobe(player, playerWardrobe);

        commandSender.sendMessage(ChatColor.GREEN + "Updated your character's description successfully!");

        return true;
    }

}
