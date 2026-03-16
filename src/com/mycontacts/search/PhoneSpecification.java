package com.mycontacts.search;

import com.mycontacts.common.Contact;

// matches contacts that have a phone number containing the keyword
public class PhoneSpecification implements Specification<Contact> {
    private final String keyword;

    public PhoneSpecification(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean isSatisfiedBy(Contact contact) {
        return contact.getPhoneNumbers().stream()
                .anyMatch(p -> p.getNumber().contains(keyword));
    }
}
