package com.mycontacts.viewcontact;

import com.mycontacts.common.Contact;

// decorator that uppercases the contact name in output
public class UpperCaseDecorator extends DisplayDecorator {

    public UpperCaseDecorator(ContactDisplay wrapped) {
        super(wrapped);
    }

    @Override
    public String display(Contact contact) {
        String output = wrapped.display(contact);
        // uppercase only the name portion in the first line
        String name = contact.getName();
        return output.replace(name, name.toUpperCase());
    }
}
