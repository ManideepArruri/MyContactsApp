package com.mycontacts.registration;

import com.mycontacts.common.User;
import com.mycontacts.common.UserRepository;

import java.util.Scanner;

// console UI for user registration flow
public class RegistrationMenu {
    private final RegistrationService registrationService;
    private final Scanner scanner;

    public RegistrationMenu(UserRepository userRepository, Scanner scanner) {
        this.registrationService = new RegistrationService(userRepository);
        this.scanner = scanner;
    }

    public void show() {
        System.out.println("\n===== User Registration =====");

        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter password (min 6 chars): ");
        String password = scanner.nextLine().trim();

        System.out.print("Account type (free/premium): ");
        String type = scanner.nextLine().trim();

        try {
            User user = registrationService.register(name, email, password, type);
            System.out.println("Registration successful!");
            System.out.println("Welcome, " + user.getName() + " [" + user.getUserType() + " account]");
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }
}
