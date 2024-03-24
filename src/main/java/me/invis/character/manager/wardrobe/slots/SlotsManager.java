package me.invis.character.manager.wardrobe.slots;

import me.invis.character.Characters;
import me.invis.character.manager.wardrobe.WardrobesManager;
import me.invis.character.manager.wardrobe.impl.Wardrobe;
import org.bukkit.entity.Player;

public class SlotsManager {

    private final Player player;
    private final WardrobesManager wardrobesManager = Characters.inst().getWardrobesManager();

    public SlotsManager(Player player) {
        this.player = player;
    }

    private Player getPlayer() {
        return player;
    }

    private WardrobesManager getWardrobesManager() {
        return wardrobesManager;
    }

    public int maximumEligibleSlots() {
        int slots = 2;
        for (WardrobeSlotsCap wsc : WardrobeSlotsCap.values())
            if (getPlayer().hasPermission(wsc.toString())) slots = wsc.getNumber();

        return slots;
    }

    public int slotsLeft() {
        if(!getWardrobesManager().hasWardrobe(getPlayer())) return maximumEligibleSlots();
        Wardrobe wardrobe = getWardrobesManager().getPlayerWardrobe(getPlayer());
        return maximumEligibleSlots() - wardrobe.getContents().length;
    }

}
