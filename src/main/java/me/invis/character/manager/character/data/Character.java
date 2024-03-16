package me.invis.character.manager.character.data;

import java.io.Serializable;

public class Character implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String name, description;
    private int age;

    public Character(String name, String description, int age) {
        this.name = name;
        this.description = description;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int newAge) {
        this.age = newAge;
    }

    public boolean equals(Character character) {
        return character.getName().equals(getName()) && character.getDescription().equals(getDescription()) && character.getAge() == getAge();
    }
}
