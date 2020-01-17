package com.skalyter.mytraveljournal.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;

    public LoggedInUser(String userId, String displayName, String userEmail, String userFirstName, String userLastName) {
        this.userId = userId;
        this.displayName = displayName;
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
