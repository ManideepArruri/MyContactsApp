package com.mycontacts.search;

import com.mycontacts.common.Contact;

// matches contacts that have an email containing the keyword (case-insensitive)
public class EmailSpecification implements Specification<Contact> {
    private final String keyword;

    public EmailSpecification(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean isSatisfiedBy(Contact contact) {
        return contact.getEmails().stream()
                .anyMatch(e -> e.getAddress().toLowerCase().contains(keyword));
    }
}
