package com.mycontacts.contact;

import com.mycontacts.common.*;

// factory to create the correct contact subtype
public class ContactFactory {

    public static Contact createContact(ContactBuilder builder) {
        Contact contact = switch (builder.getContactType().toLowerCase()) {
            case "organization", "org" -> {
                if (builder.getOrgName() == null || builder.getOrgName().trim().isEmpty()) {
                    throw new IllegalArgumentException("Organization name is required.");
                }
                Organization org = new Organization(builder.getOrgName());
                if (builder.getIndustry() != null) org.setIndustry(builder.getIndustry());
                if (builder.getWebsite() != null) org.setWebsite(builder.getWebsite());
                yield org;
            }
            default -> {
                if (builder.getFirstName() == null || builder.getFirstName().trim().isEmpty()) {
                    throw new IllegalArgumentException("First name is required.");
                }
                String last = builder.getLastName() != null ? builder.getLastName() : "";
                Person person = new Person(builder.getFirstName(), last);
                if (builder.getNickname() != null) person.setNickname(builder.getNickname());
                yield person;
            }
        };

        // add phones and emails from builder
        builder.getPhones().forEach(contact::addPhone);
        builder.getEmails().forEach(contact::addEmail);
        if (builder.getNotes() != null) contact.setNotes(builder.getNotes());

        return contact;
    }
}
