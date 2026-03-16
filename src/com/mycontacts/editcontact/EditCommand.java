package com.mycontacts.editcontact;

// command interface for contact edit operations with undo/redo
public interface EditCommand {
    void execute();
    void undo();
    String getDescription();
}
