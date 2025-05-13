package com.example.firstaidapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firstaidapp.R;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView emergencyContacts = view.findViewById(R.id.tv_emergency_contacts);

        emergencyContacts.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new EmergencyContactsFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        TextView firstAidArticles = view.findViewById(R.id.tv_guide_articles);

        firstAidArticles.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new FirstAidArticlesFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
        TextView videoTutorials = view.findViewById(R.id.tv_video_tutorials);
        videoTutorials.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new VideoTutorialsFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
        return view;
    }
}
