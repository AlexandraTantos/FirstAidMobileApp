package com.example.firstaidapp.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firstaidapp.ViewModel.ProfileViewModel;
import com.example.firstaidapp.R;
import com.example.firstaidapp.Service.ApiService;
import com.example.firstaidapp.Service.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private ImageView profilePicture;
    private Uri imageUri;
    private EditText fullName, bloodType, allergies, medicalHistory, medication, age;
    private Button btnSave, btnChangePhoto;
    private ApiService apiService;

    private View profileFragment;

    private final String PROFILE_KEY = "Userprofile_%s";

    public EditProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        profileFragment = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePicture = view.findViewById(R.id.profile_picture);
        fullName = view.findViewById(R.id.full_name);
        bloodType = view.findViewById(R.id.blood_type);
        allergies = view.findViewById(R.id.allergies);
        medicalHistory = view.findViewById(R.id.medical_history);
        medication = view.findViewById(R.id.medication);
        age = view.findViewById(R.id.age);

        btnSave = view.findViewById(R.id.btn_save);
        btnChangePhoto = view.findViewById(R.id.btn_change_photo);

        apiService = RetrofitClient.getApiService();

        btnChangePhoto.setOnClickListener(v -> openGallery());

        btnSave.setOnClickListener(v -> saveProfile());

        return view;
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            profilePicture.setImageURI(imageUri);
        }
    }

    private void saveProfile() {
        String _name = fullName.getText().toString().trim();
        String _bloodType = bloodType.getText().toString().trim();
        String _allergies = allergies.getText().toString().trim();
        String _age = age.getText().toString().trim();
        String _medication = medication.getText().toString().trim();
        String _medicalHistory = medicalHistory.getText().toString().trim();

        if (TextUtils.isEmpty(_name) || TextUtils.isEmpty(_bloodType) || TextUtils.isEmpty(_allergies) || TextUtils.isEmpty(_age)) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(_medicalHistory)) {
            _medicalHistory = "No major medical issues";
        }

        if (TextUtils.isEmpty(_medication)) {
            _medication = "No current medication";
        }

        ProfileViewModel updatedProfile = new ProfileViewModel();
        updatedProfile.setBloodType(_bloodType);
        updatedProfile.setFullName(_name);
        updatedProfile.setAllergies(_allergies);
        updatedProfile.setMedication(_medication);
        updatedProfile.setMedicalHistory(_medicalHistory);

        var intAge = Integer.parseInt(_age);
        updatedProfile.setAge(intAge);

        var idFromSession = getUserIdFromSession();
        var id = Integer.parseInt(idFromSession);


        apiService.updateProfile(id, updatedProfile).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    refreshSharedPrefs(updatedProfile, idFromSession);
                    loadProfileData(updatedProfile);

                    Toast.makeText(getActivity(), "Profile updated!", Toast.LENGTH_SHORT).show();

                    getParentFragmentManager().popBackStack();
                } else {
                    if (response.code() == 404) {
                        apiService.createProfile(id, updatedProfile).enqueue(new Callback<>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    refreshSharedPrefs(updatedProfile, idFromSession);
                                    loadProfileData(updatedProfile);

                                    Toast.makeText(getActivity(), "Profile created successfully!", Toast.LENGTH_SHORT).show();

                                    getParentFragmentManager().popBackStack();
                                } else {
                                    Toast.makeText(getActivity(), "Failed to create profile!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Profile update failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProfileData(ProfileViewModel profile) {
        TextView tvName = profileFragment.findViewById(R.id.name);
        TextView tvMedicalHistory = profileFragment.findViewById(R.id.medical_history);
        TextView tvAllergies = profileFragment.findViewById(R.id.allergies);
        TextView tvMedication = profileFragment.findViewById(R.id.medication);
        TextView tvBloodType = profileFragment.findViewById(R.id.blood_type);
        TextView tvAge = profileFragment.findViewById(R.id.age);

        tvName.setText(profile.getFullName());
        String age = String.valueOf(profile.getAge());
        tvAge.setText(String.format("%s years old", age));
        tvMedicalHistory.setText(profile.getMedicalHistory());
        tvAllergies.setText(String.format("Allergies: %s", profile.getAllergies()));
        tvMedication.setText(String.format("Medication: %s", profile.getMedication()));
        tvBloodType.setText(String.format("Blood Type: %s", profile.getBloodType()));
    }

    private void refreshSharedPrefs(ProfileViewModel profile, String userId) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(String.format(PROFILE_KEY, userId));

        Gson gson = new Gson();
        String serializedProfile = gson.toJson(profile);

        editor.putString(String.format(PROFILE_KEY, userId), serializedProfile);
        editor.apply();
    }

    private String getUserIdFromSession() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userId", "");
    }
}
