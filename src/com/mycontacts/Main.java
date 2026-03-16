package com.mycontacts;

import com.mycontacts.authentication.LoginMenu;
import com.mycontacts.authentication.SessionManager;
import com.mycontacts.common.UserRepository;
import com.mycontacts.profile.ProfileMenu;
import com.mycontacts.registration.RegistrationMenu;

import java.util.Scanner;

// entry point — main menu with enhanced switch
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserRepository userRepository = new UserRepository();

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
                System.out.println("4. Logout (" + session.getCurrentUser().getName() + ")");
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
