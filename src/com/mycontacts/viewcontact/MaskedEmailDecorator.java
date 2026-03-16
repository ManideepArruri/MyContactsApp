package com.mycontacts.viewcontact;

import com.mycontacts.common.Contact;
import com.mycontacts.common.Email;

// decorator that masks email addresses (e.g. j***@gmail.com)
public class MaskedEmailDecorator extends DisplayDecorator {

    public MaskedEmailDecorator(ContactDisplay wrapped) {
        super(wrapped);
    }

    @Override
    public String display(Contact contact) {
        String output = wrapped.display(contact);
        // mask each email address found in the output
        for (Email email : contact.getEmails()) {
            String original = email.getAddress();
            String masked = maskEmail(original);
            output = output.replace(original, masked);
        }
        return output;
    }

    // keeps first char, masks middle, keeps domain
    private String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) return email; // too short to mask

        String local = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        return local.charAt(0) + "***" + domain;
    }
}
