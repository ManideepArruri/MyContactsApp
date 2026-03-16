package com.mycontacts.tags;

import com.mycontacts.common.Contact;

// observer interface for UI updates when tags change
public interface TagObserver {
    void onTagAdded(Contact contact, Tag tag);

    void onTagRemoved(Contact contact, Tag tag);
}