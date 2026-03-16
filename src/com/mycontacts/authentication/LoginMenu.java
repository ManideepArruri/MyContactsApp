package com.mycontacts.authentication;

import com.mycontacts.common.User;
import com.mycontacts.common.UserRepository;

import java.util.Optional;
import java.util.Scanner;

// console UI for user login
public class LoginMenu {
    private final AuthenticationStrategy authStrategy;
    private final Scanner scanner;

    public LoginMenu(UserRepository userRepository, Scanner scanner) {
        this.authStrategy = new BasicAuth(userRepository);
        this.scanner = scanner;
    }

    public void show() {
        System.out.println("\n===== User Login =====");

        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();

        Optional<User> result = authStrategy.authenticate(email, password);

        if (result.isPresent()) {
            User user = result.get();
            SessionManager.getInstance().login(user);
            System.out.println("Login successful! Welcome back, " + user.getName());
        } else {
            System.out.println("Login failed: Invalid email or password.");
        }
    }
}
