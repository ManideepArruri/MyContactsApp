package com.mycontacts.tags;

import com.mycontacts.common.Contact;
import java.time.LocalDateTime;

// association class for Contact-Tag relationship
// manages bidirectional relationship and metadata
public class ContactTag {
    private final Contact contact;
    private final Tag tag;
    private final LocalDateTime assignedAt;

    public ContactTag(Contact contact, Tag tag) {
        this.contact = contact;
        this.tag = tag;
        this.assignedAt = LocalDateTime.now();
    }

    public Contact getContact() {
        return contact;
    }

    public Tag getTag() {
        return tag;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ContactTag that = (ContactTag) o;
        return contact.getId().equals(that.contact.getId()) &&
                tag.getName().equals(that.tag.getName());
    }

    @Override
    public int hashCode() {
        return contact.getId().hashCode() + tag.getName().hashCode();
    }

    @Override
    public String toString() {
        return "ContactTag{" +
                "contact=" + contact.getName() +
                ", tag=" + tag.getName() +
                ", assignedAt=" + assignedAt +
                '}';
    }
}