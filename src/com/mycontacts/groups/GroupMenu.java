package com.mycontacts.groups;

import com.mycontacts.authentication.SessionManager;
import com.mycontacts.common.Contact;
import com.mycontacts.common.ContactRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// console UI for group management and bulk operations
public class GroupMenu {
    private final ContactRepository contactRepository;
    private final Scanner scanner;
    private final List<ContactGroup> groups = new ArrayList<>();

    public GroupMenu(ContactRepository contactRepository, Scanner scanner) {
        this.contactRepository = contactRepository;
        this.scanner = scanner;
    }

    // share groups list across calls
    public List<ContactGroup> getGroups() {
        return groups;
    }

    public void show() {
        if (!SessionManager.getInstance().isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n===== Contact Groups =====");
            System.out.println("1. Create Group");
            System.out.println("2. View Groups");
            System.out.println("3. Add Contact to Group");
            System.out.println("4. Remove Contact from Group");
            System.out.println("5. Bulk Delete Group Contacts");
            System.out.println("6. Add Sub-Group");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> createGroup();
                case "2" -> viewGroups();
                case "3" -> addContactToGroup();
                case "4" -> removeContactFromGroup();
                case "5" -> bulkDeleteGroup();
                case "6" -> addSubGroup();
                case "0" -> inMenu = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void createGroup() {
        System.out.print("Group name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        groups.add(new ContactGroup(name));
        System.out.println("Group '" + name + "' created.");
    }

    private void viewGroups() {
        if (groups.isEmpty()) {
            System.out.println("No groups created yet.");
            return;
        }
        System.out.println("\nAll Groups:");
        for (ContactGroup group : groups) {
            group.displayAll();
        }
    }

    private ContactGroup selectGroup(String prompt) {
        if (groups.isEmpty()) {
            System.out.println("No groups available.");
            return null;
        }
        for (int i = 0; i < groups.size(); i++) {
            System.out.printf("  %d. %s (%d contacts)%n", i + 1,
                    groups.get(i).getComponentName(),
                    groups.get(i).getContactCount());
        }
        System.out.print(prompt);
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx >= 0 && idx < groups.size()) return groups.get(idx);
        } catch (NumberFormatException ignored) {}
        System.out.println("Invalid selection.");
        return null;
    }

    private void addContactToGroup() {
        ContactGroup group = selectGroup("Select group: ");
        if (group == null) return;

        List<Contact> contacts = contactRepository.getActiveContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        for (int i = 0; i < contacts.size(); i++) {
            System.out.printf("  %d. [%s] %s%n", i + 1,
                    contacts.get(i).getContactType(),
                    contacts.get(i).getName());
        }
        System.out.print("Select contact: ");

        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx >= 0 && idx < contacts.size()) {
                group.add(new ContactLeaf(contacts.get(idx)));
                System.out.println("Contact added to group.");
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid number.");
        }
    }

    private void removeContactFromGroup() {
        ContactGroup group = selectGroup("Select group: ");
        if (group == null) return;

        var children = group.getChildren();
        if (children.isEmpty()) {
            System.out.println("Group is empty.");
            return;
        }

        for (int i = 0; i < children.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, children.get(i).getComponentName());
        }
        System.out.print("Select to remove: ");

        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx >= 0 && idx < children.size()) {
                group.remove(children.get(idx));
                System.out.println("Removed from group.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid number.");
        }
    }

    // bulk soft-delete all contacts in a group
    private void bulkDeleteGroup() {
        ContactGroup group = selectGroup("Select group to bulk delete: ");
        if (group == null) return;

        System.out.printf("Soft-delete all %d contacts in '%s'? (yes/no): ",
                group.getContactCount(), group.getComponentName());
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {
            bulkSoftDelete(group);
            System.out.println("All contacts in group soft-deleted.");
        } else {
            System.out.println("Cancelled.");
        }
    }

    // recursively soft-delete all contacts in a group
    private void bulkSoftDelete(ContactComponent component) {
        if (component instanceof ContactLeaf leaf) {
            leaf.getContact().setDeleted(true);
        } else if (component instanceof ContactGroup group) {
            for (ContactComponent child : group.getChildren()) {
                bulkSoftDelete(child);
            }
        }
    }

    private void addSubGroup() {
        ContactGroup parent = selectGroup("Select parent group: ");
        if (parent == null) return;

        System.out.print("Sub-group name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        ContactGroup subGroup = new ContactGroup(name);
        parent.add(subGroup);
        groups.add(subGroup); // also track it as a top-level group for easy access
        System.out.println("Sub-group '" + name + "' added to '" + parent.getComponentName() + "'.");
    }
}
