package com.mycontacts.filtersort;

import com.mycontacts.common.Contact;

import java.util.Comparator;

// strategy interface for sorting contacts
public interface SortStrategy {
    Comparator<Contact> getComparator();
    String getDescription();
}
