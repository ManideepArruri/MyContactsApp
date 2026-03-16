package com.mycontacts.tags;

import com.mycontacts.authentication.SessionManager;
import com.mycontacts.common.Contact;
import com.mycontacts.common.ContactRepository;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

// menu for applying tags to contacts - implements observer pattern for UI updates
public class ApplyTagsMenu implements TagObserver {
    private final Scanner scanner;
    private final ContactRepository contactRepo;
    private final ContactTagManager tagManager;

    public ApplyTagsMenu(ContactRepository contactRepo, Scanner scanner) {
        this.contactRepo = contactRepo;
        this.scanner = scanner;
        this.tagManager = ContactTagManager.getInstance();
        // register as observer
        tagManager.addObserver(this);
    }

    public void show() {
        if (!SessionManager.getInstance().isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n===== Apply Tags to Contacts =====");
            System.out.println("1. View Contact Tags");
            System.out.println("2. Add Tag to Contact");
            System.out.println("3. Remove Tag from Contact");
            System.out.println("4. View All Tagged Contacts");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> viewContactTags();
                case "2" -> addTagToContact();
                case "3" -> removeTagFromContact();
                case "4" -> viewAllTaggedContacts();
                case "0" -> inMenu = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void viewContactTags() {
        List<Contact> contacts = contactRepo.getAllContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("\nSelect a contact to view tags:");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i).getName());
        }
        System.out.print("Contact number: ");

        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index >= 0 && index < contacts.size()) {
                Contact contact = contacts.get(index);
                Set<Tag> tags = tagManager.getTagsForContact(contact);

                System.out.println("\nTags for " + contact.getName() + ":");
                if (tags.isEmpty()) {
                    System.out.println("  No tags assigned.");
                } else {
                    tags.forEach(tag -> System.out.println("  " + tag));
                }
            } else {
                System.out.println("Invalid contact number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void addTagToContact() {
        // select contact
        List<Contact> contacts = contactRepo.getAllContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("\nSelect a contact:");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i).getName());
        }
        System.out.print("Contact number: ");

        try {
            int contactIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (contactIndex < 0 || contactIndex >= contacts.size()) {
                System.out.println("Invalid contact number.");
                return;
            }

            Contact contact = contacts.get(contactIndex);

            // select tag
            List<Tag> allTags = TagFactory.getAllTags().stream().toList();
            if (allTags.isEmpty()) {
                System.out.println("No tags available. Create some tags first.");
                return;
            }

            System.out.println("\nAvailable tags:");
            for (int i = 0; i < allTags.size(); i++) {
                Tag tag = allTags.get(i);
                boolean hasTag = tagManager.contactHasTag(contact, tag);
                System.out.println((i + 1) + ". " + tag + (hasTag ? " (already assigned)" : ""));
            }
            System.out.print("Tag number: ");

            int tagIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (tagIndex < 0 || tagIndex >= allTags.size()) {
                System.out.println("Invalid tag number.");
                return;
            }

            Tag selectedTag = allTags.get(tagIndex);

            if (tagManager.contactHasTag(contact, selectedTag)) {
                System.out.println("Contact already has this tag.");
                return;
            }

            // add tag using set operation
            if (tagManager.addTagToContact(contact, selectedTag)) {
                System.out.println("Tag '" + selectedTag.getName() + "' added to " + contact.getName());
            } else {
                System.out.println("Failed to add tag.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void removeTagFromContact() {
        // select contact
        List<Contact> contacts = contactRepo.getAllContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("\nSelect a contact:");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i).getName());
        }
        System.out.print("Contact number: ");

        try {
            int contactIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (contactIndex < 0 || contactIndex >= contacts.size()) {
                System.out.println("Invalid contact number.");
                return;
            }

            Contact contact = contacts.get(contactIndex);
            Set<Tag> contactTags = tagManager.getTagsForContact(contact);

            if (contactTags.isEmpty()) {
                System.out.println("Contact has no tags to remove.");
                return;
            }

            // select tag to remove
            System.out.println("\nTags for " + contact.getName() + ":");
            List<Tag> tagList = contactTags.stream().toList();
            for (int i = 0; i < tagList.size(); i++) {
                System.out.println((i + 1) + ". " + tagList.get(i));
            }
            System.out.print("Tag number to remove: ");

            int tagIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (tagIndex < 0 || tagIndex >= tagList.size()) {
                System.out.println("Invalid tag number.");
                return;
            }

            Tag tagToRemove = tagList.get(tagIndex);

            // remove tag using set operation
            if (tagManager.removeTagFromContact(contact, tagToRemove)) {
                System.out.println("Tag '" + tagToRemove.getName() + "' removed from " + contact.getName());
            } else {
                System.out.println("Failed to remove tag.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void viewAllTaggedContacts() {
        List<Tag> allTags = TagFactory.getAllTags().stream().toList();
        if (allTags.isEmpty()) {
            System.out.println("No tags available.");
            return;
        }

        System.out.println("\nAll tagged contacts:");
        boolean hasTaggedContacts = false;

        for (Tag tag : allTags) {
            Set<Contact> contacts = tagManager.getContactsForTag(tag);
            if (!contacts.isEmpty()) {
                hasTaggedContacts = true;
                System.out.println("Tag: " + tag);
                contacts.forEach(contact -> System.out
                        .println("  - " + contact.getName() + " (" + contact.getContactType() + ")"));
                System.out.println();
            }
        }

        if (!hasTaggedContacts) {
            System.out.println("No contacts have been tagged yet.");
        }
    }

    // observer pattern implementation - UI updates when tags change
    @Override
    public void onTagAdded(Contact contact, Tag tag) {
        System.out.println("[UI Update] Tag '" + tag.getName() + "' added to contact '" + contact.getName() + "'");
    }

    @Override
    public void onTagRemoved(Contact contact, Tag tag) {
        System.out.println("[UI Update] Tag '" + tag.getName() + "' removed from contact '" + contact.getName() + "'");
    }
}