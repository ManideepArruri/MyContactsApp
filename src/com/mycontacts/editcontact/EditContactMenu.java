package com.mycontacts.editcontact;

import com.mycontacts.authentication.SessionManager;
import com.mycontacts.common.Contact;
import com.mycontacts.common.ContactRepository;

import java.util.List;
import java.util.Scanner;

// console UI for editing contacts with undo/redo
public class EditContactMenu {
    private final ContactRepository contactRepository;
    private final Scanner scanner;

    public EditContactMenu(ContactRepository contactRepository, Scanner scanner) {
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
            System.out.println("\nNo contacts to edit.");
            return;
        }

        // list contacts for selection
        System.out.println("\n===== Edit Contact =====");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.printf("  %d. [%s] %s%n", i + 1,
                    contacts.get(i).getContactType(),
                    contacts.get(i).getName());
        }
        System.out.print("Select contact to edit (or 0 to cancel): ");

        String input = scanner.nextLine().trim();
        if (input.equals("0")) return;

        try {
            int index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= contacts.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            editContact(contacts.get(index));
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid number.");
        }
    }

    private void editContact(Contact contact) {
        CommandHistory history = new CommandHistory();
        // save initial state as memento
        ContactMemento initialState = new ContactMemento(contact);

        boolean editing = true;
        while (editing) {
            System.out.println("\nEditing: " + contact.getName());
            System.out.println("1. Edit Name");
            System.out.println("2. Add Phone");
            System.out.println("3. Add Email");
            System.out.println("4. Edit Notes");
            System.out.println("5. Undo");
            System.out.println("6. Redo");
            System.out.println("7. View Edit History");
            System.out.println("8. Restore Original (Memento)");
            System.out.println("0. Done Editing");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("New name: ");
                    String name = scanner.nextLine().trim();
                    if (!name.isEmpty()) {
                        history.executeCommand(new EditNameCommand(contact, name));
                    }
                }
                case "2" -> {
                    System.out.print("Phone label (Mobile/Home/Work): ");
                    String label = scanner.nextLine().trim();
                    System.out.print("Phone number: ");
                    String number = scanner.nextLine().trim();
                    if (!label.isEmpty() && !number.isEmpty()) {
                        history.executeCommand(new EditPhoneCommand(contact, label, number));
                    }
                }
                case "3" -> {
                    System.out.print("Email label (Personal/Work): ");
                    String label = scanner.nextLine().trim();
                    System.out.print("Email address: ");
                    String address = scanner.nextLine().trim();
                    try {
                        history.executeCommand(new EditEmailCommand(contact, label, address));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case "4" -> {
                    System.out.print("New notes: ");
                    String notes = scanner.nextLine().trim();
                    history.executeCommand(new EditNotesCommand(contact, notes));
                }
                case "5" -> history.undo();
                case "6" -> history.redo();
                case "7" -> history.showHistory();
                case "8" -> {
                    // restore from memento
                    restoreFromMemento(contact, initialState);
                    System.out.println("Contact restored to original state.");
                }
                case "0" -> editing = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // restore contact state from memento snapshot
    private void restoreFromMemento(Contact contact, ContactMemento memento) {
        contact.setName(memento.getName());
        contact.setNotes(memento.getNotes());
        // clear and re-add phones
        while (!contact.getPhoneNumbers().isEmpty()) {
            contact.removePhone(0);
        }
        memento.getPhones().forEach(contact::addPhone);
        // clear and re-add emails
        while (!contact.getEmails().isEmpty()) {
            contact.removeEmail(0);
        }
        memento.getEmails().forEach(contact::addEmail);
    }
}
