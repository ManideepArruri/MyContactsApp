package com.mycontacts.groups;

import com.mycontacts.common.Contact;

// leaf — wraps a single contact as a ContactComponent
public class ContactLeaf implements ContactComponent {
    private final Contact contact;

    public ContactLeaf(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    @Override
    public String getComponentName() {
        return contact.getName();
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- [" + contact.getContactType() + "] " + contact.getName());
    }

    @Override
    public int getContactCount() {
        return 1;
    }
}
