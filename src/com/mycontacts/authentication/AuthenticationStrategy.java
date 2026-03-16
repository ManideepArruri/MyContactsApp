package com.mycontacts.authentication;

import com.mycontacts.common.User;

import java.util.Optional;

// strategy interface for different authentication methods
public interface AuthenticationStrategy {
    Optional<User> authenticate(String email, String password);
}
