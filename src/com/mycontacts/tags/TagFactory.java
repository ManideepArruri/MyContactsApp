package com.mycontacts.tags;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// flyweight factory — caches and reuses Tag instances by name
public class TagFactory {
    private static final Map<String, Tag> tagCache = new HashMap<>();

    // static initializer loads predefined tags into cache
    static {
        for (PredefinedTag pt : PredefinedTag.values()) {
            tagCache.put(pt.getTagName(), new Tag(pt.getTagName(), pt.getColor()));
        }
    }

    // returns existing tag or creates a new one
    public static Tag getTag(String name) {
        String key = name.toLowerCase();
        return tagCache.computeIfAbsent(key, k -> new Tag(k, "Gray"));
    }

    // create tag with a custom color
    public static Tag getTag(String name, String color) {
        String key = name.toLowerCase();
        return tagCache.computeIfAbsent(key, k -> new Tag(k, color));
    }

    // all tags in the system
    public static Collection<Tag> getAllTags() {
        return tagCache.values();
    }

    // remove a custom tag (predefined tags cannot be removed)
    public static boolean removeTag(String name) {
        String key = name.toLowerCase();
        // check if predefined
        for (PredefinedTag pt : PredefinedTag.values()) {
            if (pt.getTagName().equals(key)) {
                System.out.println("Cannot remove predefined tag: " + key);
                return false;
            }
        }
        return tagCache.remove(key) != null;
    }

    // get cache size (for demonstrating flyweight reuse)
    public static int getCacheSize() {
        return tagCache.size();
    }
}
