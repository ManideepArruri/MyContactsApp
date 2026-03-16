package com.mycontacts.profile;

import java.util.ArrayList;
import java.util.List;

// stores executed commands for undo capability
public class ProfileCommandHistory {
    private final List<ProfileCommand> history = new ArrayList<>();

    public void push(ProfileCommand command) {
        history.add(command);
    }

    // undo the last command if any
    public boolean undoLast() {
        if (history.isEmpty()) {
            System.out.println("Nothing to undo.");
            return false;
        }
        ProfileCommand last = history.remove(history.size() - 1);
        last.undo();
        return true;
    }

    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No changes made yet.");
            return;
        }
        System.out.println("Change history:");
        for (int i = 0; i < history.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + history.get(i).getDescription());
        }
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }
}
