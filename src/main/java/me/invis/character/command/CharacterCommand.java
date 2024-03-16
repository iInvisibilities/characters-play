package me.invis.character.command;

import me.invis.character.Characters;
import me.invis.character.command.util.CommandUtil;
import me.invis.character.manager.character.CharactersManager;
import me.invis.character.manager.character.data.Character;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CharacterCommand implements CommandExecutor {

    CharactersManager charactersManager = Characters.inst().getCharactersManager();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!CommandUtil.isPlayer(commandSender, strings)) return true;

        Player player = Bukkit.getPlayer(strings[0]);
        assert player != null;

        if(!charactersManager.hasCharacter(player)) {
            commandSender.sendMessage(ChatColor.RED + "Player currently doesn't have any character set!");
            return true;
        }

        Character playerCharacter = charactersManager.getPlayerCharacter(player);
        commandSender.sendMessage(
                "",
                ChatColor.YELLOW + strings[0] + "'s Character",
                "",
                ChatColor.GRAY + "  Name: " + ChatColor.RESET + playerCharacter.getName(),
                ChatColor.GRAY + "  Age: " + ChatColor.RESET + playerCharacter.getAge(),
                ChatColor.GRAY + "  Description: " + ChatColor.RESET + playerCharacter.getDescription(),
                ""
        );


        return true;
    }
}
