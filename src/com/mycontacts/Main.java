package com.mycontacts;

import com.mycontacts.authentication.LoginMenu;
import com.mycontacts.authentication.SessionManager;
import com.mycontacts.common.ContactRepository;
import com.mycontacts.common.UserRepository;
import com.mycontacts.contact.CreateContactMenu;
import com.mycontacts.deletecontact.DeleteContactMenu;
import com.mycontacts.editcontact.EditContactMenu;
import com.mycontacts.filtersort.FilterSortMenu;
import com.mycontacts.groups.GroupMenu;
import com.mycontacts.profile.ProfileMenu;
import com.mycontacts.registration.RegistrationMenu;
import com.mycontacts.search.SearchMenu;
import com.mycontacts.tags.ApplyTagsMenu;
import com.mycontacts.tags.TagMenu;
import com.mycontacts.viewcontact.ViewContactMenu;

import java.util.Scanner;

// entry point — main menu with enhanced switch
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserRepository userRepository = new UserRepository();
        ContactRepository contactRepository = new ContactRepository();
        GroupMenu groupMenu = new GroupMenu(contactRepository, scanner);

        System.out.println("=============================");
        System.out.println("   Welcome to MyContacts App");
        System.out.println("=============================");

        boolean running = true;
        while (running) {
            SessionManager session = SessionManager.getInstance();

            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            if (session.isLoggedIn()) {
                System.out.println("3. My Profile");
                System.out.println("4. Create Contact");
                System.out.println("5. View Contacts");
                System.out.println("6. Edit Contact");
                System.out.println("7. Delete Contact");
                System.out.println("8. Contact Groups");
                System.out.println("9. Search Contacts");
                System.out.println("10. Filter & Sort");
                System.out.println("11. Manage Tags");
                System.out.println("12. Apply Tags to Contacts");
                System.out.println("13. Logout (" + session.getCurrentUser().getName() + ")");
            }
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();

            running = switch (input) {
                case "1" -> {
                    new RegistrationMenu(userRepository, scanner).show();
                    yield true;
                }
                case "2" -> {
                    if (session.isLoggedIn()) {
                        System.out.println("Already logged in as " + session.getCurrentUser().getName());
                    } else {
                        new LoginMenu(userRepository, scanner).show();
                    }
                    yield true;
                }
                case "3" -> {
                    if (session.isLoggedIn()) {
                        new ProfileMenu(scanner).show();
                    } else {
                        System.out.println("Please login first.");
                    }
                    yield true;
                }
                case "4" -> {
                    if (session.isLoggedIn()) {
                        new CreateContactMenu(contactRepository, scanner).show();
                    } else {
                        System.out.println("Please login first.");
                    }
                    yield true;
                }
                case "5" -> {
                    if (session.isLoggedIn()) {
                        new ViewContactMenu(contactRepository, scanner).show();
                    } else {
                        System.out.println("Please login first.");
                    }
                    yield true;
                }
                case "6" -> {
                    if (session.isLoggedIn()) {
                        new EditContactMenu(contactRepository, scanner).show();
                    } else {
                        System.out.println("Please login first.");
                    }
                    yield true;
                }
                case "7" -> {
                    if (session.isLoggedIn()) {
                        new DeleteContactMenu(contactRepository, scanner).show();
                    } else {
                        System.out.println("Please login first.");
                    }
                    yield true;
                }
                case "8" -> {
                    if (session.isLoggedIn()) {
                        groupMenu.show();
                    } else {
                        System.out.println("Please login first.");
                    }
                    yield true;
                }
                case "9" -> {
                    if (session.isLoggedIn()) {
                        new SearchMenu(contactRepository, scanner).show();
                    } else {
                        System.out.println("Please login first.");
                    }
                    yield true;
                }
                case "10" -> {
                    if (session.isLoggedIn()) {
                        new FilterSortMenu(contactRepository, scanner).show();
                    } else {
                        System.out.println("Please login first.");
                    }
                    yield true;
                }
                case "11" -> {
                    if (session.isLoggedIn()) {
                        new TagMenu(scanner).show();
                    } else {
                        System.out.println("Please login first.");
                    }
                    yield true;
                }
                case "12" -> {
                    if (session.isLoggedIn()) {
                        new ApplyTagsMenu(contactRepository, scanner).show();
                    } else {
                        System.out.println("Please login first.");
                    }
                    yield true;
                }
                case "13" -> {
                    if (session.isLoggedIn()) {
                        System.out.println("Logged out. Bye, " + session.getCurrentUser().getName() + "!");
                        session.logout();
                    } else {
                        System.out.println("Invalid option. Try again.");
                    }
                    yield true;
                }
                case "0" -> {
                    System.out.println("Goodbye!");
                    yield false;
                }
                default -> {
                    System.out.println("Invalid option. Try again.");
                    yield true;
                }
            };
        }

        scanner.close();
    }
}
