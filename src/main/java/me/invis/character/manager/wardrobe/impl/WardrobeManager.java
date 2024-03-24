package me.invis.character.manager.wardrobe.impl;

import me.invis.character.manager.character.impl.Character;
import org.apache.commons.lang3.ArrayUtils;

public class WardrobeManager {

    private final Wardrobe wardrobe;

    public WardrobeManager(Wardrobe wardrobe) {
        this.wardrobe = wardrobe;
    }

    public Wardrobe getWardrobe() {
        return wardrobe;
    }

    public void addCharacter(Character character) {
        wardrobe.updateContents(ArrayUtils.add(wardrobe.getContents(), character));
    }

    public void removeCharacter(Character character) {
        wardrobe.updateContents(ArrayUtils.remove(wardrobe.getContents(), indexOf(character)));
    }

    public Character getCharacter(int index) {
        return wardrobe.getContents()[index];
    }

    private int indexOf(Character character) {
        int ind = 0;
        for (int i = 0; i < wardrobe.getContents().length; i++) {
            if(wardrobe.getContents()[i].equals(character)) ind = i;
        }

        return ind;
    }

}
