package com.example.firstaidapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstaidapp.ViewModel.FirstAidGuideViewModel;
import com.example.firstaidapp.Service.ApiService;
import com.example.firstaidapp.Service.RetrofitClient;
import com.example.firstaidapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeartAttackFragment extends Fragment {

    private TextView infoTextView;

    private TextView stepsTextView;

    private TextView emergencyTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_heart_attack, container, false);

        infoTextView = rootView.findViewById(R.id.text_heart_attack_info);
        stepsTextView = rootView.findViewById(R.id.text_heart_attack_steps);
        emergencyTextView = rootView.findViewById(R.id.text_heart_attack_emergency);

        loadFirstAidGuide();

        return rootView;
    }
    private void loadFirstAidGuide() {
        ApiService apiService = RetrofitClient.getApiService();

        int FRAGMENT_ID = 3;
        Call<FirstAidGuideViewModel> call = apiService.getFirstAidGuide(FRAGMENT_ID);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<FirstAidGuideViewModel> call, @NonNull Response<FirstAidGuideViewModel> response) {
                if (response.isSuccessful()) {
                    FirstAidGuideViewModel guide = response.body();
                    assert guide != null;

                    infoTextView.setText(guide.getText1());
                    stepsTextView.setText(guide.getText2());
                    emergencyTextView.setText(guide.getText3());

                } else {
                    Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<FirstAidGuideViewModel> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}