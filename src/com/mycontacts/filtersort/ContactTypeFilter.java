package com.mycontacts.filtersort;

import com.mycontacts.common.Contact;

import java.util.List;
import java.util.stream.Collectors;

// filters contacts by type (Person or Organization)
public class ContactTypeFilter implements FilterStrategy {
    private final String type;

    public ContactTypeFilter(String type) {
        this.type = type;
    }

    @Override
    public List<Contact> filter(List<Contact> contacts) {
        return contacts.stream()
                .filter(c -> c.getContactType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    @Override
    public String getDescription() {
        return "Type: " + type;
    }
}
