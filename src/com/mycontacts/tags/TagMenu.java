package com.mycontacts.tags;

import com.mycontacts.authentication.SessionManager;

import java.util.Scanner;

// console UI for managing tags (create, view, delete)
public class TagMenu {
    private final Scanner scanner;

    public TagMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {
        if (!SessionManager.getInstance().isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n===== Tag Management =====");
            System.out.println("1. View All Tags");
            System.out.println("2. Create Custom Tag");
            System.out.println("3. Delete Custom Tag");
            System.out.println("4. View Cache Stats (Flyweight)");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> viewAllTags();
                case "2" -> createTag();
                case "3" -> deleteTag();
                case "4" -> showCacheStats();
                case "0" -> inMenu = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void viewAllTags() {
        var tags = TagFactory.getAllTags();
        if (tags.isEmpty()) {
            System.out.println("No tags available.");
            return;
        }
        System.out.println("\nAll tags (" + tags.size() + "):");
        // separate predefined and custom
        System.out.println("  Predefined:");
        for (PredefinedTag pt : PredefinedTag.values()) {
            Tag tag = TagFactory.getTag(pt.getTagName());
            System.out.println("    " + tag);
        }
        // custom tags
        var customTags = tags.stream()
                .filter(t -> {
                    for (PredefinedTag pt : PredefinedTag.values()) {
                        if (pt.getTagName().equals(t.getName())) return false;
                    }
                    return true;
                })
                .toList();
        if (!customTags.isEmpty()) {
            System.out.println("  Custom:");
            customTags.forEach(t -> System.out.println("    " + t));
        }
    }

    private void createTag() {
        System.out.print("Tag name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        System.out.print("Tag color (or press Enter for Gray): ");
        String color = scanner.nextLine().trim();
        if (color.isEmpty()) color = "Gray";

        Tag tag = TagFactory.getTag(name, color);
        System.out.println("Tag ready: " + tag + " (flyweight cached)");
    }

    private void deleteTag() {
        System.out.print("Tag name to delete: ");
        String name = scanner.nextLine().trim();
        if (TagFactory.removeTag(name)) {
            System.out.println("Tag '" + name + "' removed.");
        } else {
            System.out.println("Could not remove tag (not found or predefined).");
        }
    }

    private void showCacheStats() {
        System.out.println("\nFlyweight cache size: " + TagFactory.getCacheSize() + " tag(s)");
        System.out.println("All cached tags share the same instance per name.");
        System.out.println("Requesting the same tag name always returns the same object.");
        // demo
        Tag t1 = TagFactory.getTag("demo");
        Tag t2 = TagFactory.getTag("demo");
        System.out.println("  TagFactory.getTag(\"demo\") == TagFactory.getTag(\"demo\"): " + (t1 == t2));
    }
}
