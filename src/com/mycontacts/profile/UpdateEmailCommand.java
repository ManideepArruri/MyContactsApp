package com.mycontacts.profile;

import com.mycontacts.common.User;

// command to update user email with undo support
public class UpdateEmailCommand implements ProfileCommand {
    private final User user;
    private final String newEmail;
    private final String oldEmail;

    public UpdateEmailCommand(User user, String newEmail) {
        this.user = user;
        this.newEmail = newEmail;
        this.oldEmail = user.getEmail();
    }

    @Override
    public void execute() {
        // validate email format before applying
        if (!newEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            System.out.println("Invalid email format. Update cancelled.");
            return;
        }
        user.setEmail(newEmail);
        System.out.println("Email updated to: " + newEmail);
    }

    @Override
    public void undo() {
        user.setEmail(oldEmail);
        System.out.println("Email reverted to: " + oldEmail);
    }

    @Override
    public String getDescription() {
        return "Update email: " + oldEmail + " -> " + newEmail;
    }
}
