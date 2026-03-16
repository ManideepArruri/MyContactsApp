package com.mycontacts.deletecontact;

import com.mycontacts.authentication.SessionManager;
import com.mycontacts.common.Contact;
import com.mycontacts.common.ContactRepository;

import java.util.List;
import java.util.Scanner;

// console UI for deleting contacts with confirmation
public class DeleteContactMenu {
    private final DeleteService deleteService;
    private final ContactRepository contactRepository;
    private final Scanner scanner;

    public DeleteContactMenu(ContactRepository contactRepository, Scanner scanner) {
        this.contactRepository = contactRepository;
        this.deleteService = new DeleteService(contactRepository);
        this.deleteService.addObserver(new DeleteLogger()); // attach logger
        this.scanner = scanner;
    }

    public void show() {
        if (!SessionManager.getInstance().isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n===== Delete Contact =====");
            System.out.println("1. Soft Delete (recoverable)");
            System.out.println("2. Hard Delete (permanent)");
            System.out.println("3. Restore Deleted Contact");
            System.out.println("4. View Deleted Contacts");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> handleDelete("soft");
                case "2" -> handleDelete("hard");
                case "3" -> handleRestore();
                case "4" -> viewDeleted();
                case "0" -> inMenu = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void handleDelete(String type) {
        List<Contact> contacts = contactRepository.getActiveContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts to delete.");
            return;
        }

        // list active contacts
        for (int i = 0; i < contacts.size(); i++) {
            System.out.printf("  %d. [%s] %s%n", i + 1,
                    contacts.get(i).getContactType(),
                    contacts.get(i).getName());
        }
        System.out.print("Select contact to delete (or 0 to cancel): ");

        String input = scanner.nextLine().trim();
        if (input.equals("0")) return;

        try {
            int index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= contacts.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            Contact target = contacts.get(index);

            // confirmation
            System.out.printf("%s delete '%s'? (yes/no): ",
                    type.equals("hard") ? "Permanently" : "Soft", target.getName());
            String confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("yes")) {
                boolean success = type.equals("hard")
                        ? deleteService.hardDelete(target.getId())
                        : deleteService.softDelete(target.getId());
                System.out.println(success ? "Contact deleted." : "Delete failed.");
            } else {
                System.out.println("Delete cancelled.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid number.");
        }
    }

    private void handleRestore() {
        List<Contact> deleted = deleteService.getDeletedContacts();
        if (deleted.isEmpty()) {
            System.out.println("No deleted contacts to restore.");
            return;
        }

        for (int i = 0; i < deleted.size(); i++) {
            System.out.printf("  %d. [%s] %s%n", i + 1,
                    deleted.get(i).getContactType(),
                    deleted.get(i).getName());
        }
        System.out.print("Select contact to restore (or 0 to cancel): ");

        String input = scanner.nextLine().trim();
        if (input.equals("0")) return;

        try {
            int index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= deleted.size()) {
                System.out.println("Invalid selection.");
                return;
            }
            deleteService.restore(deleted.get(index).getId());
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid number.");
        }
    }

    private void viewDeleted() {
        List<Contact> deleted = deleteService.getDeletedContacts();
        if (deleted.isEmpty()) {
            System.out.println("No deleted contacts.");
            return;
        }
        System.out.println("Deleted contacts:");
        deleted.forEach(c -> System.out.println("  - " + c.getName()));
    }
}
