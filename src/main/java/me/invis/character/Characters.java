package me.invis.character;

import me.invis.character.command.*;
import me.invis.character.listener.ShowPlayerCharacter;
import me.invis.character.manager.character.CharactersManager;
import me.invis.character.manager.wardrobe.WardrobesManager;
import me.invis.character.placeholders.CharactersExpansion;
import me.invis.character.signature.SignatureCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Characters extends JavaPlugin {

    private static Characters instance;

    private CharactersManager charactersManager;
    private WardrobesManager wardrobesManager;

    @Override
    public void onEnable() {
        instance = this;

        this.charactersManager = new CharactersManager(this);
        this.wardrobesManager = new WardrobesManager(this);

        getCommand("wardrobe").setExecutor(new WardrobeCommand());
        getCommand("createcharacter").setExecutor(new CreateCharacterCommand(this));
        getCommand("character").setExecutor(new CharacterCommand());
        getCommand("setcharacterage").setExecutor(new SetCharacterAgeCommand());
        getCommand("whomadethis").setExecutor(new SignatureCommand());
        getCommand("setcharacterdescription").setExecutor(new SetCharacterDescriptionCommand());
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) new CharactersExpansion().register();

        Bukkit.getPluginManager().registerEvents(new ShowPlayerCharacter(), this);

    }

    public static Characters inst() {
        return instance;
    }

    public CharactersManager getCharactersManager() {
        return charactersManager;
    }

    public WardrobesManager getWardrobesManager() {
        return wardrobesManager;
    }
}
