package com.mycontacts.authentication;

import com.mycontacts.common.User;
import com.mycontacts.common.UserRepository;

import java.util.Optional;

// basic auth — compares hashed passwords
public class BasicAuth implements AuthenticationStrategy {
    private final UserRepository userRepository;

    public BasicAuth(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();
        String inputHash = User.hashPassword(password);

        if (user.getPasswordHash().equals(inputHash)) {
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
