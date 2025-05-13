package com.example.firstaidapp.ViewModel;

public class UserLocationViewModel {
    private String latitude;
    private String longitude;

    public UserLocationViewModel(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
