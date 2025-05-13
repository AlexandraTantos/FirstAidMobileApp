package com.example.firstaidapp.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.firstaidapp.Service.ApiService;
import com.example.firstaidapp.Service.RetrofitClient;
import com.example.firstaidapp.ViewModel.ProfileViewModel;
import com.example.firstaidapp.R;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvAge, tvMedicalHistory, tvAllergies, tvMedication, tvBloodType;
    private ApiService apiService;

    private final String PROFILE_KEY = "Userprofile_%s";

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        tvName = rootView.findViewById(R.id.name);
        tvAge = rootView.findViewById(R.id.age);
        tvMedicalHistory = rootView.findViewById(R.id.medical_history);
        tvAllergies = rootView.findViewById(R.id.allergies);
        tvMedication = rootView.findViewById(R.id.medication);
        tvBloodType = rootView.findViewById(R.id.blood_type);

        Button btnEditProfile = rootView.findViewById(R.id.btn_edit_profile);

        apiService = RetrofitClient.getApiService();

        loadProfileData();

        btnEditProfile.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new EditProfileFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return rootView;
    }

    private void loadProfileData() {
        var idFromSession = getUserIdFromSession();
        var id = Integer.parseInt(idFromSession);

        ProfileViewModel profileFromPrefs = getUserProfile(idFromSession);

        if (profileFromPrefs != null) {
            loadProfileData(profileFromPrefs);
            return;
        }

        apiService.getProfile(id).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ProfileViewModel> call, Response<ProfileViewModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProfileViewModel profile = response.body();
                    Log.d("ProfileFragment", "Profile Loaded: " + profile.getFullName());

                    loadProfileData(profile);

                    saveUserProfile(profile, idFromSession);
                } else {
                    Log.e("ProfileFragment", "Failed to load profile: " + response.message());
                    Toast.makeText(getActivity(), "Failed to load My Profile.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileViewModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProfileData(ProfileViewModel profile) {
        tvName.setText(profile.getFullName());
        String age = String.valueOf(profile.getAge());
        tvAge.setText(String.format("%s years old", age));
        tvMedicalHistory.setText(profile.getMedicalHistory());
        tvAllergies.setText(String.format("Allergies: %s", profile.getAllergies()));
        tvMedication.setText(String.format("Medication: %s", profile.getMedication()));
        tvBloodType.setText(String.format("Blood Type: %s", profile.getBloodType()));
    }

    private void saveUserProfile(ProfileViewModel profile, String userId) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(String.format(PROFILE_KEY, userId));

        Gson gson = new Gson();
        String serializedProfile = gson.toJson(profile);

        editor.putString(String.format(PROFILE_KEY, userId), serializedProfile);
        editor.apply();
    }

    public ProfileViewModel getUserProfile(String userId) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String key = String.format(PROFILE_KEY, userId);
        String userJson = sharedPreferences.getString(key, null);

        if (userJson != null) {
            Gson gson = new Gson();
            return gson.fromJson(userJson, ProfileViewModel.class);
        }

        return null;
    }

    private String getUserIdFromSession() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userId", "");
    }
}
