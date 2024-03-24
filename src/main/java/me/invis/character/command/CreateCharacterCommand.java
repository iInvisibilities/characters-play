package me.invis.character.command;

import me.invis.character.Characters;
import me.invis.character.ui.charactercreation.CharacterSetupConversation;
import me.invis.character.manager.character.impl.Character;
import me.invis.character.manager.wardrobe.WardrobesManager;
import me.invis.character.manager.wardrobe.slots.SlotsManager;
import me.invis.character.manager.wardrobe.impl.Wardrobe;
import me.invis.character.manager.wardrobe.impl.WardrobeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class CreateCharacterCommand implements CommandExecutor {

    private final Plugin plugin;
    private final WardrobesManager wardrobesManager;

    public CreateCharacterCommand(Characters plugin) {
        this.plugin = plugin;
        this.wardrobesManager = plugin.getWardrobesManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = ((Player) commandSender);

        if(new SlotsManager(player).slotsLeft() <= 0) {
            player.sendMessage(ChatColor.RED + "Reached maximum character slots!");
            return true;
        }

        CharacterSetupConversation csc = new CharacterSetupConversation(plugin, player);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!player.isOnline()) {
                    cancel();
                    return;
                }
                Character character = csc.getCharacter();
                if(character != null) {
                    if(!wardrobesManager.hasWardrobe(player)) {
                        wardrobesManager.setWardrobe(player, new Wardrobe(new Character[0]));
                    }

                    WardrobeManager wardrobeManager = new WardrobeManager(wardrobesManager.getPlayerWardrobe(player));
                    wardrobeManager.addCharacter(character);
                    wardrobesManager.setWardrobe(player, wardrobeManager.getWardrobe());
                    player.sendMessage(ChatColor.GREEN + "Created " + character.getName() + "'s character successfully!");
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 20, 5);
        return true;
    }
}
