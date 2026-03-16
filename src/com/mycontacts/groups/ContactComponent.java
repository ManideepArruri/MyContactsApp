package com.mycontacts.groups;

// component interface for composite pattern — contacts and groups share this
public interface ContactComponent {
    String getComponentName();
    void display(String indent);
    int getContactCount();
}
