package com.mycontacts.groups;

import java.util.ArrayList;
import java.util.List;

// composite — a named group that holds contacts and sub-groups
public class ContactGroup implements ContactComponent {
    private final String name;
    private final List<ContactComponent> children = new ArrayList<>();

    public ContactGroup(String name) {
        this.name = name;
    }

    public void add(ContactComponent component) {
        children.add(component);
    }

    public void remove(ContactComponent component) {
        children.remove(component);
    }

    public List<ContactComponent> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public String getComponentName() {
        return name;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "+ Group: " + name + " (" + getContactCount() + " contacts)");
        for (ContactComponent child : children) {
            child.display(indent + "  ");
        }
    }

    @Override
    public int getContactCount() {
        int count = 0;
        for (ContactComponent child : children) {
            count += child.getContactCount();
        }
        return count;
    }

    // bulk display all contacts in this group
    public void displayAll() {
        display("");
    }
}
