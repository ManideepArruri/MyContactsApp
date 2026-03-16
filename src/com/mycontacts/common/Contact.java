package com.mycontacts.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// abstract base for all contacts — uses composition for phones and emails
public abstract class Contact {
    private final String id;
    private String name;
    private final List<PhoneNumber> phoneNumbers;
    private final List<Email> emails;
    private String notes;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted; // soft delete flag

    protected Contact(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.phoneNumbers = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.notes = "";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.deleted = false;
    }

    // getters
    public String getId() { return id; }
    public String getName() { return name; }
    public List<PhoneNumber> getPhoneNumbers() { return new ArrayList<>(phoneNumbers); }
    public List<Email> getEmails() { return new ArrayList<>(emails); }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public boolean isDeleted() { return deleted; }

    // setters
    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public void setNotes(String notes) {
        this.notes = notes;
        this.updatedAt = LocalDateTime.now();
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
        this.updatedAt = LocalDateTime.now();
    }

    // phone management
    public void addPhone(PhoneNumber phone) {
        phoneNumbers.add(phone);
        updatedAt = LocalDateTime.now();
    }

    public void removePhone(int index) {
        if (index >= 0 && index < phoneNumbers.size()) {
            phoneNumbers.remove(index);
            updatedAt = LocalDateTime.now();
        }
    }

    // email management
    public void addEmail(Email email) {
        emails.add(email);
        updatedAt = LocalDateTime.now();
    }

    public void removeEmail(int index) {
        if (index >= 0 && index < emails.size()) {
            emails.remove(index);
            updatedAt = LocalDateTime.now();
        }
    }

    // subclasses define their type label
    public abstract String getContactType();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s] %s\n", getContactType(), name));
        if (!phoneNumbers.isEmpty()) {
            sb.append("  Phones:\n");
            phoneNumbers.forEach(p -> sb.append("    ").append(p).append("\n"));
        }
        if (!emails.isEmpty()) {
            sb.append("  Emails:\n");
            emails.forEach(e -> sb.append("    ").append(e).append("\n"));
        }
        if (notes != null && !notes.isEmpty()) {
            sb.append("  Notes: ").append(notes).append("\n");
        }
        return sb.toString();
    }
}
