package com.mycontacts.search;

import com.mycontacts.common.Contact;

// matches contacts whose name contains the keyword (case-insensitive)
public class NameSpecification implements Specification<Contact> {
    private final String keyword;

    public NameSpecification(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean isSatisfiedBy(Contact contact) {
        return contact.getName().toLowerCase().contains(keyword);
    }
}
