package com.mycontacts.filtersort;

import com.mycontacts.authentication.SessionManager;
import com.mycontacts.common.Contact;
import com.mycontacts.common.ContactRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// console UI for filtering and sorting contacts
public class FilterSortMenu {
    private final ContactRepository contactRepository;
    private final Scanner scanner;

    public FilterSortMenu(ContactRepository contactRepository, Scanner scanner) {
        this.contactRepository = contactRepository;
        this.scanner = scanner;
    }

    public void show() {
        if (!SessionManager.getInstance().isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n===== Filter & Sort =====");
            System.out.println("1. Filter by Type (Person/Organization)");
            System.out.println("2. Filter by Date Added");
            System.out.println("3. Filter: Has Phone Number");
            System.out.println("4. Sort by Name (A-Z)");
            System.out.println("5. Sort by Date Added (Newest)");
            System.out.println("6. Sort by Type");
            System.out.println("7. Filter + Sort Combined");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("Enter type (person/organization): ");
                    String type = scanner.nextLine().trim();
                    applyFilter(new ContactTypeFilter(type));
                }
                case "2" -> {
                    System.out.print("Show contacts added on or after (YYYY-MM-DD): ");
                    String dateStr = scanner.nextLine().trim();
                    try {
                        LocalDate date = LocalDate.parse(dateStr);
                        applyFilter(new DateAddedFilter(date));
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format.");
                    }
                }
                case "3" -> applyFilter(new HasPhoneFilter());
                case "4" -> applySort(new SortByName());
                case "5" -> applySort(new SortByDateAdded());
                case "6" -> applySort(new SortByType());
                case "7" -> combinedFilterSort();
                case "0" -> inMenu = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void applyFilter(FilterStrategy filter) {
        List<Contact> contacts = contactRepository.getActiveContacts();
        List<Contact> results = filter.filter(contacts);
        displayResults("Filter: " + filter.getDescription(), results);
    }

    private void applySort(SortStrategy sort) {
        List<Contact> contacts = contactRepository.getActiveContacts();
        List<Contact> sorted = contacts.stream()
                .sorted(sort.getComparator())
                .collect(Collectors.toList());
        displayResults("Sort: " + sort.getDescription(), sorted);
    }

    // apply filter first, then sort
    private void combinedFilterSort() {
        System.out.println("Select filter:");
        System.out.println("  1. By Type  2. Has Phone  3. No filter");
        System.out.print("Filter choice: ");
        String filterChoice = scanner.nextLine().trim();

        FilterStrategy filter = switch (filterChoice) {
            case "1" -> {
                System.out.print("Type (person/organization): ");
                yield new ContactTypeFilter(scanner.nextLine().trim());
            }
            case "2" -> new HasPhoneFilter();
            default -> new FilterStrategy() {
                @Override
                public List<Contact> filter(List<Contact> contacts) { return contacts; }
                @Override
                public String getDescription() { return "None"; }
            };
        };

        System.out.println("Select sort:");
        System.out.println("  1. Name  2. Date Added  3. Type");
        System.out.print("Sort choice: ");
        String sortChoice = scanner.nextLine().trim();

        SortStrategy sort = switch (sortChoice) {
            case "2" -> new SortByDateAdded();
            case "3" -> new SortByType();
            default -> new SortByName();
        };

        List<Contact> contacts = contactRepository.getActiveContacts();
        List<Contact> filtered = filter.filter(contacts);
        List<Contact> result = filtered.stream()
                .sorted(sort.getComparator())
                .collect(Collectors.toList());
        displayResults("Filter + Sort", result);
    }

    private void displayResults(String label, List<Contact> contacts) {
        System.out.println("\n" + label + " — " + contacts.size() + " result(s):");
        if (contacts.isEmpty()) {
            System.out.println("  No contacts match.");
        } else {
            for (Contact c : contacts) {
                System.out.printf("  [%s] %s (added %s)%n",
                        c.getContactType(), c.getName(),
                        c.getCreatedAt().toLocalDate());
            }
        }
    }
}
