package com.mycontacts.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.UUID;

// base user class with core fields and password hashing
public abstract class User {
    private final String id;
    private String name;
    private String email;
    private String passwordHash;
    private final LocalDateTime createdAt;

    protected User(String name, String email, String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.passwordHash = hashPassword(password);
        this.createdAt = LocalDateTime.now();
    }

    // SHA-256 hashing for password storage
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    // subclasses define their user type label
    public abstract String getUserType();

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - joined %s",
                getUserType(), name, email,
                createdAt.toLocalDate());
    }
}
