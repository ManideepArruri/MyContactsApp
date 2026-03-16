package com.mycontacts.contact;

import com.mycontacts.authentication.SessionManager;
import com.mycontacts.common.Contact;
import com.mycontacts.common.ContactRepository;

import java.util.Scanner;

// console UI for creating new contacts
public class CreateContactMenu {
    private final ContactRepository contactRepository;
    private final Scanner scanner;

    public CreateContactMenu(ContactRepository contactRepository, Scanner scanner) {
        this.contactRepository = contactRepository;
        this.scanner = scanner;
    }

    public void show() {
        if (!SessionManager.getInstance().isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        System.out.println("\n===== Create Contact =====");
        System.out.println("1. Person");
        System.out.println("2. Organization");
        System.out.print("Contact type: ");
        String type = scanner.nextLine().trim();

        try {
            Contact contact = switch (type) {
                case "1" -> buildPerson();
                case "2" -> buildOrganization();
                default -> {
                    System.out.println("Invalid type.");
                    yield null;
                }
            };

            if (contact != null) {
                contactRepository.addContact(contact);
                System.out.println("Contact created successfully!");
                System.out.print(contact);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Contact buildPerson() {
        ContactBuilder builder = new ContactBuilder().setContactType("person");

        System.out.print("First name: ");
        builder.setFirstName(scanner.nextLine().trim());

        System.out.print("Last name: ");
        builder.setLastName(scanner.nextLine().trim());

        System.out.print("Nickname (or press Enter to skip): ");
        String nick = scanner.nextLine().trim();
        if (!nick.isEmpty()) builder.setNickname(nick);

        collectPhones(builder);
        collectEmails(builder);

        System.out.print("Notes (or press Enter to skip): ");
        String notes = scanner.nextLine().trim();
        if (!notes.isEmpty()) builder.setNotes(notes);

        return builder.build();
    }

    private Contact buildOrganization() {
        ContactBuilder builder = new ContactBuilder().setContactType("organization");

        System.out.print("Organization name: ");
        builder.setOrgName(scanner.nextLine().trim());

        System.out.print("Industry (or press Enter to skip): ");
        String industry = scanner.nextLine().trim();
        if (!industry.isEmpty()) builder.setIndustry(industry);

        System.out.print("Website (or press Enter to skip): ");
        String website = scanner.nextLine().trim();
        if (!website.isEmpty()) builder.setWebsite(website);

        collectPhones(builder);
        collectEmails(builder);

        System.out.print("Notes (or press Enter to skip): ");
        String notes = scanner.nextLine().trim();
        if (!notes.isEmpty()) builder.setNotes(notes);

        return builder.build();
    }

    // collect multiple phone numbers
    private void collectPhones(ContactBuilder builder) {
        System.out.println("Add phone numbers (enter blank to stop):");
        while (true) {
            System.out.print("  Phone label (Mobile/Home/Work): ");
            String label = scanner.nextLine().trim();
            if (label.isEmpty()) break;

            System.out.print("  Phone number: ");
            String number = scanner.nextLine().trim();
            if (number.isEmpty()) break;

            builder.addPhone(label, number);
            System.out.println("  Phone added.");
        }
    }

    // collect multiple email addresses
    private void collectEmails(ContactBuilder builder) {
        System.out.println("Add email addresses (enter blank to stop):");
        while (true) {
            System.out.print("  Email label (Personal/Work): ");
            String label = scanner.nextLine().trim();
            if (label.isEmpty()) break;

            System.out.print("  Email address: ");
            String address = scanner.nextLine().trim();
            if (address.isEmpty()) break;

            try {
                builder.addEmail(label, address);
                System.out.println("  Email added.");
            } catch (IllegalArgumentException e) {
                System.out.println("  " + e.getMessage());
            }
        }
    }
}
