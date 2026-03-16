package com.mycontacts.viewcontact;

import com.mycontacts.common.Contact;

// default formatter — uses Contact's toString()
public class BaseContactDisplay implements ContactDisplay {

    @Override
    public String display(Contact contact) {
        return contact.toString();
    }
}
