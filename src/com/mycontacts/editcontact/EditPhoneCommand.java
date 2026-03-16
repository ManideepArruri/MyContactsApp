package com.mycontacts.editcontact;

import com.mycontacts.common.Contact;
import com.mycontacts.common.PhoneNumber;

// command to add a phone number to a contact
public class EditPhoneCommand implements EditCommand {
    private final Contact contact;
    private final PhoneNumber newPhone;
    private boolean added = false;

    public EditPhoneCommand(Contact contact, String label, String number) {
        this.contact = contact;
        this.newPhone = new PhoneNumber(label, number);
    }

    @Override
    public void execute() {
        contact.addPhone(newPhone);
        added = true;
        System.out.println("Phone added: " + newPhone);
    }

    @Override
    public void undo() {
        if (added) {
            // remove the last phone (the one we added)
            var phones = contact.getPhoneNumbers();
            contact.removePhone(phones.size() - 1);
            added = false;
            System.out.println("Phone removed: " + newPhone);
        }
    }

    @Override
    public String getDescription() {
        return "Add phone: " + newPhone;
    }
}
