package com.mycontacts.registration;

import com.mycontacts.common.FreeUser;
import com.mycontacts.common.PremiumUser;
import com.mycontacts.common.User;

// factory to create correct user subtype based on type string
public class UserFactory {

    public static User createUser(UserBuilder builder) {
        return switch (builder.getUserType().toLowerCase()) {
            case "premium" -> new PremiumUser(
                    builder.getName(),
                    builder.getEmail(),
                    builder.getPassword()
            );
            default -> new FreeUser(
                    builder.getName(),
                    builder.getEmail(),
                    builder.getPassword()
            );
        };
    }
}
