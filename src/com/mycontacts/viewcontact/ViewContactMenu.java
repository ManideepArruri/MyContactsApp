package com.mycontacts.viewcontact;

import com.mycontacts.authentication.SessionManager;
import com.mycontacts.common.Contact;
import com.mycontacts.common.ContactRepository;

import java.util.List;
import java.util.Scanner;

// console UI for viewing contacts with optional decorators
public class ViewContactMenu {
    private final ContactRepository contactRepository;
    private final Scanner scanner;

    public ViewContactMenu(ContactRepository contactRepository, Scanner scanner) {
        this.contactRepository = contactRepository;
        this.scanner = scanner;
    }

    public void show() {
        if (!SessionManager.getInstance().isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        List<Contact> contacts = contactRepository.getActiveContacts();
        if (contacts.isEmpty()) {
            System.out.println("\nNo contacts found. Create one first.");
            return;
        }

        boolean viewing = true;
        while (viewing) {
            System.out.println("\n===== View Contacts =====");
            // list all contacts with index
            for (int i = 0; i < contacts.size(); i++) {
                System.out.printf("  %d. [%s] %s%n", i + 1,
                        contacts.get(i).getContactType(),
                        contacts.get(i).getName());
            }
            System.out.println("  0. Back to Main Menu");
            System.out.print("Select a contact to view (or 0): ");

            String input = scanner.nextLine().trim();
            if (input.equals("0")) {
                viewing = false;
                continue;
            }

            try {
                int index = Integer.parseInt(input) - 1;
                if (index < 0 || index >= contacts.size()) {
                    System.out.println("Invalid selection.");
                    continue;
                }

                Contact selected = contacts.get(index);
                showWithDecorators(selected);

            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    // let user choose display format with decorators
    private void showWithDecorators(Contact contact) {
        System.out.println("\nDisplay format:");
        System.out.println("1. Default");
        System.out.println("2. UpperCase Name");
        System.out.println("3. Masked Email");
        System.out.println("4. UpperCase + Masked Email");
        System.out.print("Choose format: ");

        String choice = scanner.nextLine().trim();

        // build decorator chain based on choice
        ContactDisplay display = switch (choice) {
            case "2" -> new UpperCaseDecorator(new BaseContactDisplay());
            case "3" -> new MaskedEmailDecorator(new BaseContactDisplay());
            case "4" -> new MaskedEmailDecorator(
                    new UpperCaseDecorator(new BaseContactDisplay()));
            default -> new BaseContactDisplay();
        };

        System.out.println("\n--- Contact Details ---");
        System.out.print(display.display(contact));
        System.out.println("-----------------------");
    }
}
