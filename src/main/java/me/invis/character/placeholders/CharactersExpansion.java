package me.invis.character.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.invis.character.Characters;
import me.invis.character.manager.character.CharactersManager;
import me.invis.character.manager.character.impl.Character;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CharactersExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "character";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Invisibilities";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player p, @NotNull String identifier) {
        CharactersManager charactersManager = Characters.inst().getCharactersManager();
        if(!charactersManager.hasCharacter(p)) return "No character";
        Character character = charactersManager.getPlayerCharacter(p);

        switch (identifier) {
            case "description": return character.getDescription();
            case "age": return String.valueOf(character.getAge());
            default: return character.getName();
        }
    }
}
