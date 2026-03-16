package com.mycontacts.registration;

import com.mycontacts.common.User;
import com.mycontacts.common.UserRepository;

// handles registration logic — validation, duplicate checks, user creation
public class RegistrationService {
    private final UserRepository userRepository;

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // registers a new user, throws if email already taken
    public User register(String name, String email, String password, String userType) {
        if (userRepository.emailExists(email)) {
            throw new IllegalArgumentException("Email '" + email + "' is already registered.");
        }

        User user = new UserBuilder()
                .setName(name)
                .setEmail(email)
                .setPassword(password)
                .setUserType(userType)
                .build();

        userRepository.addUser(user);
        return user;
    }
}
