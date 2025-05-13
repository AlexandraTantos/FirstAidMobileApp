package com.example.firstaidapp.ViewModel;

public class RegisterRequestViewModel {
    private String username;
    private String password;

    public RegisterRequestViewModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
