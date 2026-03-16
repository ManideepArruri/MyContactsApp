package com.mycontacts.profile;

import com.mycontacts.common.User;

// command to update user name with undo support
public class UpdateNameCommand implements ProfileCommand {
    private final User user;
    private final String newName;
    private final String oldName;

    public UpdateNameCommand(User user, String newName) {
        this.user = user;
        this.newName = newName;
        this.oldName = user.getName();
    }

    @Override
    public void execute() {
        user.setName(newName);
        System.out.println("Name updated to: " + newName);
    }

    @Override
    public void undo() {
        user.setName(oldName);
        System.out.println("Name reverted to: " + oldName);
    }

    @Override
    public String getDescription() {
        return "Update name: " + oldName + " -> " + newName;
    }
}
