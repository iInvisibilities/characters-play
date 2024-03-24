package me.invis.character.ui.wardrobe.item;

import me.invis.character.Characters;
import me.invis.character.manager.character.CharactersManager;
import me.invis.character.manager.character.impl.Character;
import me.invis.character.manager.wardrobe.WardrobesManager;
import me.invis.character.manager.wardrobe.impl.WardrobeManager;
import me.invis.character.ui.wardrobe.WardrobeGUI;
import me.invis.character.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CharacterItem extends AbstractItem {

    private final CharactersManager charactersManager = Characters.inst().getCharactersManager();
    private final WardrobesManager wardrobesManager = Characters.inst().getWardrobesManager();

    private final Character character;
    private final Player player;

    private Material type;

    public CharacterItem(Character character, Player player) {
        this.character = character;
        this.player = player;
        type = isCurrentlyPlaying() ? Material.ENDER_EYE : Material.ENDER_PEARL;
    }

    private boolean isCurrentlyPlaying() {
        return charactersManager.getPlayerCharacter(player) != null && charactersManager.getPlayerCharacter(player).equals(getCharacter());
    }

    private Character getCharacter() {
        return character;
    }

    @Override
    public ItemProvider getItemProvider() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Age: " + getCharacter().getAge());
        lore.add("");
        lore.add(ChatColor.GRAY + "Description: ");
        lore.addAll(Stream.of(
                StringUtil.splitPresenvingWords(getCharacter().getDescription(), 60))
                .map(l -> ChatColor.GRAY + "  " + l).collect(Collectors.toList()));
        lore.add("");
        lore.add(ChatColor.GREEN + "Left-Click to select as your character!");
        lore.add(ChatColor.RED + "Shift-Left-Click to delete this character from your wardrobe!");

        return new ItemBuilder(type)
                .setDisplayName(
                        (isCurrentlyPlaying() ? ChatColor.GREEN + "✔ " : ChatColor.GRAY) + getCharacter().getName())
                .setLegacyLore(lore);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        if(clickType == ClickType.LEFT) {
            if(type == Material.BARRIER) {
                player.sendMessage(ChatColor.RED + "Character is deleted and can't be retrieved!");
                return;
            } else if (type == Material.ENDER_EYE) {
                charactersManager.clearPlayerCharacter(player);
                player.sendMessage(ChatColor.GREEN + "Unselected current character!");
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, .8F, 1);
                new WardrobeGUI(player);
                return;
            }

            charactersManager.applyCharacterToPlayer(getCharacter(), player);
            player.sendMessage(ChatColor.GREEN + "You're now " + getCharacter().getName() + "!");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, .8F, .5F);
            new WardrobeGUI(player);
        } else if (clickType == ClickType.SHIFT_LEFT) {
            Character playerChar = charactersManager.getPlayerCharacter(player);
            if(type == Material.BARRIER) return;
            if(playerChar != null && playerChar.equals(getCharacter())) {
                player.sendMessage(ChatColor.RED + "You can't delete this character!");
                return;
            }

            type = Material.BARRIER;
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, .8F, .5F);

            WardrobeManager wardrobeManager = new WardrobeManager(wardrobesManager.getPlayerWardrobe(player));
            wardrobeManager.removeCharacter(getCharacter());
            wardrobesManager.setWardrobe(player, wardrobeManager.getWardrobe());

            notifyWindows();
        }
    }
}
