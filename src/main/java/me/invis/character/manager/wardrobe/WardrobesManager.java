package me.invis.character.manager.wardrobe;

import me.invis.character.manager.wardrobe.wardrobe.Wardrobe;
import me.invis.character.manager.wardrobe.data.WardrobeDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WardrobesManager {

    private final Plugin plugin;
    private final NamespacedKey wardrobeKey;

    public WardrobesManager(Plugin plugin) {
        this.plugin = plugin;
        this.wardrobeKey = new NamespacedKey(plugin, "wardrobe");
    }

    private Plugin getPlugin() {
        return plugin;
    }

    private NamespacedKey getWardrobeKey() {
        return wardrobeKey;
    }

    public Wardrobe getPlayerWardrobe(Player player) {
        return player.getPersistentDataContainer().get(getWardrobeKey(), new WardrobeDataType());
    }

    public void removePlayerWardrobe(Player player) {
        player.getPersistentDataContainer().remove(getWardrobeKey());
    }

    public boolean hasWardrobe(Player player) {
        return player.getPersistentDataContainer().has(getWardrobeKey(), new WardrobeDataType());
    }

    public void setWardrobe(Player player, Wardrobe wardrobe) {
        player.getPersistentDataContainer().set(getWardrobeKey(), new WardrobeDataType(), wardrobe);
    }

}
