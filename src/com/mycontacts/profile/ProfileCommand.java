package com.mycontacts.profile;

// command interface for profile operations with undo
public interface ProfileCommand {
    void execute();
    void undo();
    String getDescription();
}
