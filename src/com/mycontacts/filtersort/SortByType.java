package com.mycontacts.filtersort;

import com.mycontacts.common.Contact;

import java.util.Comparator;

// sorts contacts by type (Organization before Person, or vice versa)
public class SortByType implements SortStrategy {

    @Override
    public Comparator<Contact> getComparator() {
        return Comparator.comparing(Contact::getContactType);
    }

    @Override
    public String getDescription() {
        return "Type (Organization → Person)";
    }
}
