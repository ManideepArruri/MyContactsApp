package com.mycontacts.contact;

import com.mycontacts.common.*;

import java.util.ArrayList;
import java.util.List;

// builder for step-by-step contact construction
public class ContactBuilder {
    private String contactType = "person"; // default
    private String firstName;
    private String lastName;
    private String orgName;
    private String nickname;
    private String industry;
    private String website;
    private String notes;
    private final List<PhoneNumber> phones = new ArrayList<>();
    private final List<Email> emails = new ArrayList<>();

    public ContactBuilder setContactType(String type) {
        this.contactType = type;
        return this;
    }

    public ContactBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ContactBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactBuilder setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public ContactBuilder setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public ContactBuilder setIndustry(String industry) {
        this.industry = industry;
        return this;
    }

    public ContactBuilder setWebsite(String website) {
        this.website = website;
        return this;
    }

    public ContactBuilder setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public ContactBuilder addPhone(String label, String number) {
        phones.add(new PhoneNumber(label, number));
        return this;
    }

    public ContactBuilder addEmail(String label, String address) {
        emails.add(new Email(label, address));
        return this;
    }

    // getters for factory
    public String getContactType() { return contactType; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getOrgName() { return orgName; }
    public String getNickname() { return nickname; }
    public String getIndustry() { return industry; }
    public String getWebsite() { return website; }
    public String getNotes() { return notes; }
    public List<PhoneNumber> getPhones() { return phones; }
    public List<Email> getEmails() { return emails; }

    public Contact build() {
        return ContactFactory.createContact(this);
    }
}
