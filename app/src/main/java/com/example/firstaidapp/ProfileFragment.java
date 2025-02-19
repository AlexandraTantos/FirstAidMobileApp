package com.example.firstaidapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvAge, tvMedicalHistory, tvAllergies, tvMedication;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView tvName = rootView.findViewById(R.id.tv_name);
        TextView tvAge = rootView.findViewById(R.id.tv_age);
        TextView tvMedicalHistory = rootView.findViewById(R.id.tv_medical_history);
        TextView tvAllergies = rootView.findViewById(R.id.tv_allergies);
        TextView tvMedication = rootView.findViewById(R.id.tv_medication);

        tvName.setText("Alexandra T");
        tvAge.setText("25");
        tvMedicalHistory.setText("No significant medical history");
        tvAllergies.setText("Peanut allergy");
        tvMedication.setText("None");

        return rootView;
    }

}
