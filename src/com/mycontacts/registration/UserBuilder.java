package com.mycontacts.registration;

import com.mycontacts.common.User;

// builder for step-by-step user construction with validation
public class UserBuilder {
    private String name;
    private String email;
    private String password;
    private String userType = "free"; // default

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    // getters for factory to access
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getUserType() { return userType; }

    // validates all required fields before build
    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
    }

    public User build() {
        validate();
        return UserFactory.createUser(this);
    }
}
