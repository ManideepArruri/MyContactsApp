package com.mycontacts.filtersort;

import com.mycontacts.common.Contact;

import java.util.Comparator;

// sorts contacts by creation date (newest first)
public class SortByDateAdded implements SortStrategy {

    @Override
    public Comparator<Contact> getComparator() {
        return Comparator.comparing(Contact::getCreatedAt).reversed();
    }

    @Override
    public String getDescription() {
        return "Date Added (Newest First)";
    }
}
