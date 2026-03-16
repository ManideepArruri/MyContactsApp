package com.mycontacts.editcontact;

import com.mycontacts.common.Contact;

// command to edit contact notes
public class EditNotesCommand implements EditCommand {
    private final Contact contact;
    private final String newNotes;
    private final String oldNotes;

    public EditNotesCommand(Contact contact, String newNotes) {
        this.contact = contact;
        this.newNotes = newNotes;
        this.oldNotes = contact.getNotes();
    }

    @Override
    public void execute() {
        contact.setNotes(newNotes);
        System.out.println("Notes updated.");
    }

    @Override
    public void undo() {
        contact.setNotes(oldNotes);
        System.out.println("Notes reverted.");
    }

    @Override
    public String getDescription() {
        return "Edit notes";
    }
}
