package com.mycontacts.filtersort;

import com.mycontacts.common.Contact;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// filters contacts added after a given date
public class DateAddedFilter implements FilterStrategy {
    private final LocalDate afterDate;

    public DateAddedFilter(LocalDate afterDate) {
        this.afterDate = afterDate;
    }

    @Override
    public List<Contact> filter(List<Contact> contacts) {
        return contacts.stream()
                .filter(c -> c.getCreatedAt().toLocalDate().isAfter(afterDate)
                        || c.getCreatedAt().toLocalDate().isEqual(afterDate))
                .collect(Collectors.toList());
    }

    @Override
    public String getDescription() {
        return "Added on or after " + afterDate;
    }
}
