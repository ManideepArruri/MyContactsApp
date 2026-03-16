package com.mycontacts.common;

// value object for phone numbers with label
public class PhoneNumber {
    private String label; // e.g. "Home", "Work", "Mobile"
    private String number;

    public PhoneNumber(String label, String number) {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty.");
        }
        this.label = label;
        this.number = number.trim();
    }

    public String getLabel() { return label; }
    public String getNumber() { return number; }
    public void setLabel(String label) { this.label = label; }
    public void setNumber(String number) { this.number = number; }

    @Override
    public String toString() {
        return label + ": " + number;
    }
}
