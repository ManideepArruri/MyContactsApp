package com.mycontacts.filtersort;

import com.mycontacts.common.Contact;

import java.util.List;

// strategy interface for filtering contacts
public interface FilterStrategy {
    List<Contact> filter(List<Contact> contacts);
    String getDescription();
}
