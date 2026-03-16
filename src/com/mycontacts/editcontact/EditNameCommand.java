package com.mycontacts.editcontact;

import com.mycontacts.common.Contact;

// command to rename a contact
public class EditNameCommand implements EditCommand {
    private final Contact contact;
    private final String newName;
    private final String oldName;

    public EditNameCommand(Contact contact, String newName) {
        this.contact = contact;
        this.newName = newName;
        this.oldName = contact.getName();
    }

    @Override
    public void execute() {
        contact.setName(newName);
        System.out.println("Name updated to: " + newName);
    }

    @Override
    public void undo() {
        contact.setName(oldName);
        System.out.println("Name reverted to: " + oldName);
    }

    @Override
    public String getDescription() {
        return "Rename: " + oldName + " -> " + newName;
    }
}
