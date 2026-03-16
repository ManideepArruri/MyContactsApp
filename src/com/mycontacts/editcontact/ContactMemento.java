package com.mycontacts.editcontact;

import com.mycontacts.common.Contact;
import com.mycontacts.common.Email;
import com.mycontacts.common.PhoneNumber;

import java.util.ArrayList;
import java.util.List;

// memento — immutable snapshot of a contact's state
public class ContactMemento {
    private final String name;
    private final String notes;
    private final List<PhoneNumber> phones;
    private final List<Email> emails;

    public ContactMemento(Contact contact) {
        this.name = contact.getName();
        this.notes = contact.getNotes();
        // deep copy lists to preserve state
        this.phones = new ArrayList<>(contact.getPhoneNumbers());
        this.emails = new ArrayList<>(contact.getEmails());
    }

    public String getName() { return name; }
    public String getNotes() { return notes; }
    public List<PhoneNumber> getPhones() { return new ArrayList<>(phones); }
    public List<Email> getEmails() { return new ArrayList<>(emails); }
}
