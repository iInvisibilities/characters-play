package me.invis.character.manager.wardrobe.impl;

import me.invis.character.manager.character.impl.Character;

import java.io.Serializable;

public class Wardrobe implements Serializable {

    private static final long serialVersionUID = 2L;
    private Character[] contents;

    public Wardrobe(Character[] contents) {
        this.contents = contents;
    }

    public Character[] getContents() {
        return contents;
    }

    public void updateContents(Character[] newContents) {
        this.contents = newContents;
    }

}
