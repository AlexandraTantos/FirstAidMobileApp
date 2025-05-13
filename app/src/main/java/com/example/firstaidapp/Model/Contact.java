package com.example.firstaidapp.Model;

public class Contact {
    private String name;
    private String phoneNumber;
    private int userId;

    public Contact(String name, String phone, int userId) {
        this.name = name;
        this.phoneNumber = phone;
        this.userId = userId;
    }
    public Contact() {
    }

    public String getName() {
        return name;
    }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
}
