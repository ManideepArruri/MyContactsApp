package com.mycontacts.filtersort;

import com.mycontacts.common.Contact;

import java.util.Comparator;

// sorts contacts alphabetically by name
public class SortByName implements SortStrategy {

    @Override
    public Comparator<Contact> getComparator() {
        return Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER);
    }

    @Override
    public String getDescription() {
        return "Name (A-Z)";
    }
}
