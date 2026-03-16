package com.mycontacts.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// in-memory store for registered users
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    // find user by email (case-insensitive)
    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public boolean emailExists(String email) {
        return findByEmail(email).isPresent();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users); // defensive copy
    }

    public int getUserCount() {
        return users.size();
    }
}
