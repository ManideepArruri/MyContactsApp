package com.mycontacts.profile;

import com.mycontacts.authentication.SessionManager;
import com.mycontacts.common.User;

import java.util.Scanner;

// console UI for viewing and editing user profile
public class ProfileMenu {
    private final Scanner scanner;
    private final ProfileCommandHistory commandHistory = new ProfileCommandHistory();

    public ProfileMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {
        User user = SessionManager.getInstance().getCurrentUser();
        if (user == null) {
            System.out.println("Please login first.");
            return;
        }

        boolean inProfile = true;
        while (inProfile) {
            System.out.println("\n===== Profile Management =====");
            System.out.println("Name:    " + user.getName());
            System.out.println("Email:   " + user.getEmail());
            System.out.println("Type:    " + user.getUserType());
            System.out.println("Joined:  " + user.getCreatedAt().toLocalDate());
            System.out.println("------------------------------");
            System.out.println("1. Update Name");
            System.out.println("2. Update Email");
            System.out.println("3. Change Password");
            System.out.println("4. Undo Last Change");
            System.out.println("5. View Change History");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> {
                    System.out.print("Enter new name: ");
                    String name = scanner.nextLine().trim();
                    if (!name.isEmpty()) {
                        ProfileCommand cmd = new UpdateNameCommand(user, name);
                        cmd.execute();
                        commandHistory.push(cmd);
                    } else {
                        System.out.println("Name cannot be empty.");
                    }
                }
                case "2" -> {
                    System.out.print("Enter new email: ");
                    String email = scanner.nextLine().trim();
                    ProfileCommand cmd = new UpdateEmailCommand(user, email);
                    cmd.execute();
                    commandHistory.push(cmd);
                }
                case "3" -> {
                    System.out.print("Enter current password: ");
                    String current = scanner.nextLine().trim();
                    // verify current password
                    if (!User.hashPassword(current).equals(user.getPasswordHash())) {
                        System.out.println("Current password is incorrect.");
                        break;
                    }
                    System.out.print("Enter new password (min 6 chars): ");
                    String newPass = scanner.nextLine().trim();
                    if (newPass.length() < 6) {
                        System.out.println("Password must be at least 6 characters.");
                        break;
                    }
                    ProfileCommand cmd = new ChangePasswordCommand(user, newPass);
                    cmd.execute();
                    commandHistory.push(cmd);
                }
                case "4" -> commandHistory.undoLast();
                case "5" -> commandHistory.showHistory();
                case "0" -> inProfile = false;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
