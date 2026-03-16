package com.mycontacts.viewcontact;

import com.mycontacts.common.Contact;

// abstract decorator — wraps another ContactDisplay
public abstract class DisplayDecorator implements ContactDisplay {
    protected final ContactDisplay wrapped;

    protected DisplayDecorator(ContactDisplay wrapped) {
        this.wrapped = wrapped;
    }
}
