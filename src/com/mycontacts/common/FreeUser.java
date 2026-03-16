package com.mycontacts.common;

// free-tier user with a cap on contacts
public class FreeUser extends User {
    private static final int MAX_CONTACTS = 50;
    private int contactCount;

    public FreeUser(String name, String email, String password) {
        super(name, email, password);
        this.contactCount = 0;
    }

    public int getMaxContacts() { return MAX_CONTACTS; }
    public int getContactCount() { return contactCount; }

    public boolean canAddContact() {
        return contactCount < MAX_CONTACTS;
    }

    public void incrementContactCount() {
        contactCount++;
    }

    @Override
    public String getUserType() {
        return "Free";
    }
}
