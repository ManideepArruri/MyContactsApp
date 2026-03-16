package com.mycontacts;

import com.mycontacts.common.UserRepository;
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
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Register");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();

            running = switch (input) {
                case "1" -> {
                    new RegistrationMenu(userRepository, scanner).show();
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
