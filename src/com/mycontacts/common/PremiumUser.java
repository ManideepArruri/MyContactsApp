package com.mycontacts.common;

// premium user with unlimited features
public class PremiumUser extends User {
    private boolean advancedSearchEnabled;

    public PremiumUser(String name, String email, String password) {
        super(name, email, password);
        this.advancedSearchEnabled = true;
    }

    public boolean isAdvancedSearchEnabled() {
        return advancedSearchEnabled;
    }

    public void setAdvancedSearchEnabled(boolean enabled) {
        this.advancedSearchEnabled = enabled;
    }

    @Override
    public String getUserType() {
        return "Premium";
    }
}
