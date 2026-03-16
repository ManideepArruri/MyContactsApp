package com.mycontacts.deletecontact;

import com.mycontacts.common.Contact;
import com.mycontacts.common.ContactRepository;

import java.util.ArrayList;
import java.util.List;

// handles soft and hard deletion, notifies observers
public class DeleteService {
    private final ContactRepository contactRepository;
    private final List<DeleteObserver> observers = new ArrayList<>();

    public DeleteService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void addObserver(DeleteObserver observer) {
        observers.add(observer);
    }

    // soft delete — marks contact as deleted but keeps in repo
    public boolean softDelete(String contactId) {
        var contactOpt = contactRepository.findById(contactId);
        if (contactOpt.isPresent()) {
            Contact contact = contactOpt.get();
            contact.setDeleted(true);
            notifyObservers(contact, "Soft");
            return true;
        }
        return false;
    }

    // hard delete — permanently removes contact from repo
    public boolean hardDelete(String contactId) {
        var contactOpt = contactRepository.findById(contactId);
        if (contactOpt.isPresent()) {
            Contact contact = contactOpt.get();
            contactRepository.removeContact(contactId);
            notifyObservers(contact, "Hard");
            return true;
        }
        return false;
    }

    // restore a soft-deleted contact
    public boolean restore(String contactId) {
        // search all contacts including deleted ones
        var allContacts = contactRepository.getAllContacts();
        for (Contact c : allContacts) {
            if (c.getId().equals(contactId) && c.isDeleted()) {
                c.setDeleted(false);
                System.out.println("Contact restored: " + c.getName());
                return true;
            }
        }
        return false;
    }

    // get soft-deleted contacts for restore menu
    public List<Contact> getDeletedContacts() {
        return contactRepository.getAllContacts().stream()
                .filter(Contact::isDeleted)
                .toList();
    }

    private void notifyObservers(Contact contact, String deleteType) {
        for (DeleteObserver observer : observers) {
            observer.onContactDeleted(contact, deleteType);
        }
    }
}
