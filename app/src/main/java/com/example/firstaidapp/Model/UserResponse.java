package com.example.firstaidapp.Model;

public class UserResponse {
    private String fullName;
    private int userId;
    public UserResponse(String fullName, int userId) {
        this.fullName = fullName;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
