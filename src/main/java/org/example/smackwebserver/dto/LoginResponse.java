package org.example.smackwebserver.dto;

public class LoginResponse {
    private UserDTO user;
    private String token;

    // Constructors
    public LoginResponse(UserDTO user, String token) {
        this.user = user;
        this.token = token;
    }

    // Getters and Setters
    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

