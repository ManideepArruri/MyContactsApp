package com.mycontacts.filtersort;

import com.mycontacts.common.Contact;

import java.util.List;
import java.util.stream.Collectors;

// filters contacts that have at least one phone number
public class HasPhoneFilter implements FilterStrategy {

    @Override
    public List<Contact> filter(List<Contact> contacts) {
        return contacts.stream()
                .filter(c -> !c.getPhoneNumbers().isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public String getDescription() {
        return "Has phone number";
    }
}
