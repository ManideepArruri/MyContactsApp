package com.mycontacts.editcontact;

import com.mycontacts.common.Contact;
import com.mycontacts.common.Email;

// command to add an email to a contact
public class EditEmailCommand implements EditCommand {
    private final Contact contact;
    private final Email newEmail;
    private boolean added = false;

    public EditEmailCommand(Contact contact, String label, String address) {
        this.contact = contact;
        this.newEmail = new Email(label, address);
    }

    @Override
    public void execute() {
        contact.addEmail(newEmail);
        added = true;
        System.out.println("Email added: " + newEmail);
    }

    @Override
    public void undo() {
        if (added) {
            var emails = contact.getEmails();
            contact.removeEmail(emails.size() - 1);
            added = false;
            System.out.println("Email removed: " + newEmail);
        }
    }

    @Override
    public String getDescription() {
        return "Add email: " + newEmail;
    }
}
