package me.invis.character.manager.wardrobe.slots;

public enum WardrobeSlotsCap {

    WARDROBE_SLOTS_TWO(2),
    WARDROBE_SLOTS_THREE(3),
    WARDROBE_SLOTS_FOUR(4),
    WARDROBE_SLOTS_SIX(6);

    private final int number;

    WardrobeSlotsCap(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase().replaceAll("_", ".");
    }

    public int getNumber() {
        return number;
    }
}
