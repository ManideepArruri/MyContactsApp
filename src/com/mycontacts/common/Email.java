package com.mycontacts.common;

// value object for email addresses with label
public class Email {
    private String label; // e.g. "Personal", "Work"
    private String address;

    public Email(String label, String address) {
        if (address == null || !address.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email: " + address);
        }
        this.label = label;
        this.address = address.trim();
    }

    public String getLabel() { return label; }
    public String getAddress() { return address; }
    public void setLabel(String label) { this.label = label; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return label + ": " + address;
    }
}
