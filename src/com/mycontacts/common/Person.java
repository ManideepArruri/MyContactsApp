package com.mycontacts.common;

// person contact with first/last name and nickname
public class Person extends Contact {
    private String firstName;
    private String lastName;
    private String nickname;

    public Person(String firstName, String lastName) {
        super(firstName + " " + lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getNickname() { return nickname; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setName(firstName + " " + lastName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        setName(firstName + " " + lastName);
    }

    public void setNickname(String nickname) { this.nickname = nickname; }

    @Override
    public String getContactType() {
        return "Person";
    }

    @Override
    public String toString() {
        String base = super.toString();
        if (nickname != null && !nickname.isEmpty()) {
            base = base.replaceFirst("\n", " (\"" + nickname + "\")\n");
        }
        return base;
    }
}
