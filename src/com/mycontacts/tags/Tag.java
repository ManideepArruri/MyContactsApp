package com.mycontacts.tags;

import java.util.Objects;

// flyweight — immutable tag object shared across contacts
public final class Tag {
    private final String name;
    private final String color;

    // package-private — only TagFactory creates tags
    Tag(String name, String color) {
        this.name = name.toLowerCase();
        this.color = color;
    }

    public String getName() { return name; }
    public String getColor() { return color; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return name.equals(tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "[" + color + "] " + name;
    }
}
