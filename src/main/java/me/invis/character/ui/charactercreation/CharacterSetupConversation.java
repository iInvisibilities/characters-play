package me.invis.character.ui.charactercreation;

import me.invis.character.manager.character.data.Character;
import me.invis.character.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CharacterSetupConversation {

    private Character character;

    private String name, description;
    private int age;

    public CharacterSetupConversation(Plugin plugin, Player player) {
        ConversationFactory conversationFactory = new ConversationFactory(plugin);
        conversationFactory.withFirstPrompt(askForName());
        conversationFactory.withModality(true);

        Conversation conversation = conversationFactory.buildConversation(player);
        conversation.begin();
    }

    private void setupNewCharacter() {
        character = new Character(name, description, age);
    }

    public Character getCharacter() {
        return character;
    }

    private void setName(String name) {
        this.name = name;
    }
    private void setDescription(String description) {
        this.description = description;
    }
    private void setAge(int age) {
        this.age = age;
    }

    private Prompt askForAge() {
        return new StringPrompt() {

            private boolean isAgeRestriction(ConversationContext conversationContext) {
                return !((Player) conversationContext.getForWhom()).hasPermission("character.age.restriction");
            }

            @NotNull
            @Override
            public String getPromptText(@NotNull ConversationContext conversationContext) {
                return " ⦿ Character age?" + (isAgeRestriction(conversationContext) ? " ( < 21)" : "");
            }

            @Nullable
            @Override
            public Prompt acceptInput(@NotNull ConversationContext conversationContext, @Nullable String s) {
                if(s == null) return null;
                if(!StringUtil.isInt(s)) return askForAge();

                int age = Integer.parseInt(s);
                if(isAgeRestriction(conversationContext) && age > 21) return askForAge();

                setAge(age);
                setupNewCharacter();

                return null;
            }
        };
    }

    private Prompt askForDescription() {
        return new StringPrompt() {
            @NotNull
            @Override
            public String getPromptText(@NotNull ConversationContext conversationContext) {
                return " ⦿ Character description?";
            }

            @Nullable
            @Override
            public Prompt acceptInput(@NotNull ConversationContext conversationContext, @Nullable String s) {
                if(s == null) return null;
                setDescription(StringUtil.cc(s));
                return askForAge();
            }
        };
    }

    private Prompt askForName() {
        return new StringPrompt() {
            @NotNull
            @Override
            public String getPromptText(@NotNull ConversationContext conversationContext) {
                return " ⦿ Character name?";
            }

            @Nullable
            @Override
            public Prompt acceptInput(@NotNull ConversationContext conversationContext, @Nullable String s) {
                if(s == null) return null;
                setName(s);
                return askForDescription();
            }
        };
    }

}
