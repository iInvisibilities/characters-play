package me.invis.character.manager.character;

import me.invis.character.manager.character.data.Character;
import me.invis.character.manager.character.data.CharacterDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

public class CharactersManager {

    private final NamespacedKey currentCharacterKey;

    public CharactersManager(Plugin plugin) {
        this.currentCharacterKey = new NamespacedKey(plugin, "character");
    }

    private NamespacedKey getCurrentCharacterKey() {
        return currentCharacterKey;
    }

    public void applyCharacterToPlayer(Character character, Player player) {
        PersistentDataContainer playerDataContainer = player.getPersistentDataContainer();
        playerDataContainer.set(getCurrentCharacterKey(), new CharacterDataType(), character);
    }

    public boolean hasCharacter(Player player) {
        return player.getPersistentDataContainer().has(getCurrentCharacterKey(), new CharacterDataType());
    }

    public void clearPlayerCharacter(Player player) {
        player.getPersistentDataContainer().remove(getCurrentCharacterKey());
    }

    public Character getPlayerCharacter(Player player) {
        return player.getPersistentDataContainer().get(getCurrentCharacterKey(), new CharacterDataType());
    }

}
