package com.example.firstaidapp.ViewModel;

public class LoginRequestViewModel {
    private String username;
    private String password;

    public LoginRequestViewModel(String username, String password) {
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
