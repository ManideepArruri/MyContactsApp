package com.mycontacts.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// in-memory store for contacts, scoped by user id
public class ContactRepository {
    private final List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public Optional<Contact> findById(String id) {
        return contacts.stream()
                .filter(c -> !c.isDeleted() && c.getId().equals(id))
                .findFirst();
    }

    // get all active contacts for display
    public List<Contact> getActiveContacts() {
        return contacts.stream()
                .filter(c -> !c.isDeleted())
                .collect(Collectors.toList());
    }

    // get all contacts including soft-deleted
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }

    // hard delete — permanently remove
    public boolean removeContact(String id) {
        return contacts.removeIf(c -> c.getId().equals(id));
    }

    public int getActiveCount() {
        return (int) contacts.stream().filter(c -> !c.isDeleted()).count();
    }
}
