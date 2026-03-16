package com.mycontacts.tags;

// predefined tags with default colors
public enum PredefinedTag {
    FAMILY("family", "Blue"),
    FRIEND("friend", "Green"),
    WORK("work", "Red"),
    SCHOOL("school", "Yellow"),
    VIP("vip", "Gold"),
    FAVORITE("favorite", "Pink");

    private final String name;
    private final String color;

    PredefinedTag(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getTagName() { return name; }
    public String getColor() { return color; }
}
