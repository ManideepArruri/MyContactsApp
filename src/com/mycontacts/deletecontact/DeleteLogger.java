package com.mycontacts.deletecontact;

import com.mycontacts.common.Contact;

// logs deletion events to console
public class DeleteLogger implements DeleteObserver {

    @Override
    public void onContactDeleted(Contact contact, String deleteType) {
        System.out.println("[LOG] " + deleteType + " delete: " + contact.getName()
                + " (ID: " + contact.getId().substring(0, 8) + "...)");
    }
}
