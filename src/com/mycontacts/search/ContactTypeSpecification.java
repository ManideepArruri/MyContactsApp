package com.mycontacts.search;

import com.mycontacts.common.Contact;

// matches contacts by type (Person or Organization)
public class ContactTypeSpecification implements Specification<Contact> {
    private final String type;

    public ContactTypeSpecification(String type) {
        this.type = type.toLowerCase();
    }

    @Override
    public boolean isSatisfiedBy(Contact contact) {
        return contact.getContactType().toLowerCase().equals(type);
    }
}
