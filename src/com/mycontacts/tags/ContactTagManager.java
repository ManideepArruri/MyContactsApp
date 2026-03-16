package com.mycontacts.tags;

import com.mycontacts.common.Contact;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// subject class for observer pattern - manages contact-tag relationships
// handles bidirectional relationships and notifies observers of changes
public class ContactTagManager {
    private static final ContactTagManager instance = new ContactTagManager();

    // bidirectional mapping: contact -> set of tags, tag -> set of contacts
    private final Map<String, Set<Tag>> contactTags = new ConcurrentHashMap<>();
    private final Map<String, Set<Contact>> tagContacts = new ConcurrentHashMap<>();

    // observer pattern
    private final List<TagObserver> observers = new ArrayList<>();

    private ContactTagManager() {
    }

    public static ContactTagManager getInstance() {
        return instance;
    }

    // observer management
    public void addObserver(TagObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TagObserver observer) {
        observers.remove(observer);
    }

    private void notifyTagAdded(Contact contact, Tag tag) {
        for (TagObserver observer : observers) {
            observer.onTagAdded(contact, tag);
        }
    }

    private void notifyTagRemoved(Contact contact, Tag tag) {
        for (TagObserver observer : observers) {
            observer.onTagRemoved(contact, tag);
        }
    }

    // add tag to contact (set operation)
    public boolean addTagToContact(Contact contact, Tag tag) {
        if (contact == null || tag == null)
            return false;

        String contactId = contact.getId();
        String tagName = tag.getName();

        // add to contact -> tags mapping
        contactTags.computeIfAbsent(contactId, k -> ConcurrentHashMap.newKeySet())
                .add(tag);

        // add to tag -> contacts mapping
        tagContacts.computeIfAbsent(tagName, k -> ConcurrentHashMap.newKeySet())
                .add(contact);

        // notify observers
        notifyTagAdded(contact, tag);

        return true;
    }

    // remove tag from contact (set operation)
    public boolean removeTagFromContact(Contact contact, Tag tag) {
        if (contact == null || tag == null)
            return false;

        String contactId = contact.getId();
        String tagName = tag.getName();

        // remove from contact -> tags mapping
        Set<Tag> tags = contactTags.get(contactId);
        boolean removedFromContact = tags != null && tags.remove(tag);

        // remove from tag -> contacts mapping
        Set<Contact> contacts = tagContacts.get(tagName);
        boolean removedFromTag = contacts != null && contacts.remove(contact);

        // clean up empty sets
        if (tags != null && tags.isEmpty()) {
            contactTags.remove(contactId);
        }
        if (contacts != null && contacts.isEmpty()) {
            tagContacts.remove(tagName);
        }

        // notify observers only if both removals were successful
        if (removedFromContact && removedFromTag) {
            notifyTagRemoved(contact, tag);
            return true;
        }

        return false;
    }

    // get all tags for a contact
    public Set<Tag> getTagsForContact(Contact contact) {
        if (contact == null)
            return Collections.emptySet();
        return contactTags.getOrDefault(contact.getId(), Collections.emptySet());
    }

    // get all contacts for a tag
    public Set<Contact> getContactsForTag(Tag tag) {
        if (tag == null)
            return Collections.emptySet();
        return tagContacts.getOrDefault(tag.getName(), Collections.emptySet());
    }

    // check if contact has specific tag
    public boolean contactHasTag(Contact contact, Tag tag) {
        if (contact == null || tag == null)
            return false;
        Set<Tag> tags = contactTags.get(contact.getId());
        return tags != null && tags.contains(tag);
    }

    // get all contact-tag associations
    public Set<ContactTag> getAllAssociations() {
        Set<ContactTag> associations = new HashSet<>();
        for (Map.Entry<String, Set<Tag>> entry : contactTags.entrySet()) {
            String contactId = entry.getKey();
            // Note: We need to find the contact object, but this is simplified
            // In a real implementation, we'd have a contact repository lookup
            for (Tag tag : entry.getValue()) {
                // associations.add(new ContactTag(contact, tag));
                // Skipping for now as we don't have contact lookup
            }
        }
        return associations;
    }

    // clear all relationships (useful for testing or reset)
    public void clearAll() {
        contactTags.clear();
        tagContacts.clear();
    }
}