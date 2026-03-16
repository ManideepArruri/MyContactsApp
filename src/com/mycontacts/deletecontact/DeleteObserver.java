package com.mycontacts.deletecontact;

import com.mycontacts.common.Contact;

// observer interface — notified when a contact is deleted
public interface DeleteObserver {
    void onContactDeleted(Contact contact, String deleteType);
}
