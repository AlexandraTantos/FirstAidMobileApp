package com.example.firstaidapp.Service;
import com.example.firstaidapp.Model.ApiResponse;
import com.example.firstaidapp.ViewModel.LoginRequestViewModel;
import com.example.firstaidapp.ViewModel.RegisterRequestViewModel;
import com.example.firstaidapp.ViewModel.FirstAidGuideViewModel;
import com.example.firstaidapp.ViewModel.HospitalViewModel;
import com.example.firstaidapp.ViewModel.UserLocationViewModel;
import com.example.firstaidapp.ViewModel.ProfileViewModel;
import com.example.firstaidapp.Model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ApiService {
    @POST("api/hospital/findNearestHospital")
    Call<List<HospitalViewModel>> getHospitals(@Body UserLocationViewModel userLocation);
    @GET("api/firstaidguides/{id}")
    Call<FirstAidGuideViewModel> getFirstAidGuide(@Path("id") int id);
    @POST("api/authentication/register")
    Call<ApiResponse> register(@Body RegisterRequestViewModel registerRequest);
    @POST("api/authentication/login")
    Call<String> login(@Body LoginRequestViewModel loginRequest);
    @POST("api/authentication/logout")
    Call<String> logout();
    @GET("api/profile/{userId}")
    Call<ProfileViewModel> getProfile(@Path("userId") int userId);
    @PUT("api/profile/updateProfile/{userId}")
    Call<Void> updateProfile(@Path("userId") int userId, @Body ProfileViewModel profile);
    @PUT("api/profile/createProfile/{userId}")
    Call<Void> createProfile(@Path("userId") int userId, @Body ProfileViewModel profile);
    @GET("api/emergencyContacts/getContacts/{userId}")
    Call<List<Contact>> getEmergencyContacts(@Path("userId") int userId);
    @POST("api/emergencyContacts/addContact/{userId}")
    Call<Void> addEmergencyContact(@Path("userId") int userId, @Body Contact emergencyContact);
}

