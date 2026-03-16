package com.mycontacts.search;

import com.mycontacts.common.Contact;

// generic specification interface for building composable search criteria
public interface Specification<T> {
    boolean isSatisfiedBy(T item);

    // combine specs with AND
    default Specification<T> and(Specification<T> other) {
        return item -> this.isSatisfiedBy(item) && other.isSatisfiedBy(item);
    }

    // combine specs with OR
    default Specification<T> or(Specification<T> other) {
        return item -> this.isSatisfiedBy(item) || other.isSatisfiedBy(item);
    }

    // negate a spec
    default Specification<T> not() {
        return item -> !this.isSatisfiedBy(item);
    }
}
