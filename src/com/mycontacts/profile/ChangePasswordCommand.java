package com.mycontacts.profile;

import com.mycontacts.common.User;

// command to change user password with undo support
public class ChangePasswordCommand implements ProfileCommand {
    private final User user;
    private final String newPasswordHash;
    private final String oldPasswordHash;

    public ChangePasswordCommand(User user, String newPassword) {
        this.user = user;
        this.newPasswordHash = User.hashPassword(newPassword);
        this.oldPasswordHash = user.getPasswordHash();
    }

    @Override
    public void execute() {
        user.setPasswordHash(newPasswordHash);
        System.out.println("Password updated successfully.");
    }

    @Override
    public void undo() {
        user.setPasswordHash(oldPasswordHash);
        System.out.println("Password change reverted.");
    }

    @Override
    public String getDescription() {
        return "Change password";
    }
}
