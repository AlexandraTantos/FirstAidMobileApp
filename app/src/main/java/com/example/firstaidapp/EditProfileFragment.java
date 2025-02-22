package com.example.firstaidapp;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firstaidapp.R;

public class EditProfileFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private ImageView ivProfilePicture;
    private Uri imageUri;
    private EditText etFullName, etBloodType, etAllergies, etOldPassword, etNewPassword, etConfirmPassword;
    private Button btnSave, btnChangePhoto, btnBack;

    public EditProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        ivProfilePicture = view.findViewById(R.id.iv_profile_picture);
        etFullName = view.findViewById(R.id.et_full_name);
        etBloodType = view.findViewById(R.id.et_blood_type);
        etAllergies = view.findViewById(R.id.et_allergies);
        etOldPassword = view.findViewById(R.id.et_old_password);
        etNewPassword = view.findViewById(R.id.et_new_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);
        btnSave = view.findViewById(R.id.btn_save);
        btnChangePhoto = view.findViewById(R.id.btn_change_photo);

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
            ivProfilePicture.setImageURI(imageUri);
        }
    }

    private void saveProfile() {
        String fullName = etFullName.getText().toString().trim();
        String bloodType = etBloodType.getText().toString().trim();
        String allergies = etAllergies.getText().toString().trim();
        String oldPassword = etOldPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(bloodType) || TextUtils.isEmpty(allergies)) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(oldPassword)) {
            if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(getActivity(), "Enter new password and confirm it", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

        }

        Toast.makeText(getActivity(), "Profile updated!", Toast.LENGTH_SHORT).show();
        getParentFragmentManager().popBackStack();
    }
}
