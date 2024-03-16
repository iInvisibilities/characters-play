package me.invis.character.ui.wardrobe;

import me.invis.character.Characters;
import me.invis.character.ui.wardrobe.item.CharacterItem;
import me.invis.character.manager.character.data.Character;
import me.invis.character.manager.wardrobe.WardrobesManager;
import me.invis.character.manager.wardrobe.slots.SlotsManager;
import me.invis.character.manager.wardrobe.wardrobe.Wardrobe;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

public class WardrobeGUI {

    public WardrobeGUI(Player player) {
        player.closeInventory();

        WardrobesManager wardrobesManager = Characters.inst().getWardrobesManager();
        if(!wardrobesManager.hasWardrobe(player)) {
            player.sendMessage(ChatColor.RED + "You have no characters setup yet!");
            return;
        }

        Gui playerGUI = Gui.empty(9, 1);

        Wardrobe wardrobe = wardrobesManager.getPlayerWardrobe(player);
        for (Character character : wardrobe.getContents()) {
            playerGUI.addItems(new CharacterItem(character, player));
        }

        SlotsManager slotsManager = new SlotsManager(player);
        int max = slotsManager.maximumEligibleSlots(), left = slotsManager.slotsLeft();

        String slots = (left <= 1 ? ChatColor.RED : "") + "(" + (max - left) + "/" + max + ")";

        Window window = Window.single()
                .setViewer(player)
                .setTitle(ChatColor.GRAY + "Character selection " + slots)
                .setGui(playerGUI)
                .build();

        window.open();
    }

}
