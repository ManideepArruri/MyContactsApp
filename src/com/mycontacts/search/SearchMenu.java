package com.mycontacts.search;

import com.mycontacts.authentication.SessionManager;
import com.mycontacts.common.Contact;
import com.mycontacts.common.ContactRepository;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// console UI for searching contacts using composable specifications
public class SearchMenu {
    private final ContactRepository contactRepository;
    private final Scanner scanner;

    public SearchMenu(ContactRepository contactRepository, Scanner scanner) {
        this.contactRepository = contactRepository;
        this.scanner = scanner;
    }

    public void show() {
        if (!SessionManager.getInstance().isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        boolean searching = true;
        while (searching) {
            System.out.println("\n===== Search Contacts =====");
            System.out.println("1. Search by Name");
            System.out.println("2. Search by Phone");
            System.out.println("3. Search by Email");
            System.out.println("4. Search by Type (Person/Organization)");
            System.out.println("5. Combined Search (Name AND Phone)");
            System.out.println("6. Combined Search (Name OR Email)");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("Enter name keyword: ");
                    String keyword = scanner.nextLine().trim();
                    search(new NameSpecification(keyword));
                }
                case "2" -> {
                    System.out.print("Enter phone number: ");
                    String keyword = scanner.nextLine().trim();
                    search(new PhoneSpecification(keyword));
                }
                case "3" -> {
                    System.out.print("Enter email keyword: ");
                    String keyword = scanner.nextLine().trim();
                    search(new EmailSpecification(keyword));
                }
                case "4" -> {
                    System.out.print("Enter type (person/organization): ");
                    String type = scanner.nextLine().trim();
                    search(new ContactTypeSpecification(type));
                }
                case "5" -> {
                    System.out.print("Enter name keyword: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine().trim();
                    // AND composition
                    Specification<Contact> spec = new NameSpecification(name)
                            .and(new PhoneSpecification(phone));
                    search(spec);
                }
                case "6" -> {
                    System.out.print("Enter name keyword: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter email keyword: ");
                    String email = scanner.nextLine().trim();
                    // OR composition
                    Specification<Contact> spec = new NameSpecification(name)
                            .or(new EmailSpecification(email));
                    search(spec);
                }
                case "0" -> searching = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // run a search using the given specification
    private void search(Specification<Contact> spec) {
        List<Contact> results = contactRepository.getActiveContacts().stream()
                .filter(spec::isSatisfiedBy)
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("\nFound " + results.size() + " contact(s):");
            results.forEach(c -> System.out.printf("  [%s] %s%n",
                    c.getContactType(), c.getName()));
        }
    }
}
