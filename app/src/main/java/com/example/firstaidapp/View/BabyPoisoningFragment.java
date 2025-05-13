package com.example.firstaidapp.View;
import com.example.firstaidapp.ViewModel.FirstAidGuideViewModel;
import com.example.firstaidapp.Service.RetrofitClient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstaidapp.Service.ApiService;
import com.example.firstaidapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BabyPoisoningFragment extends Fragment {

    private TextView stepsTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_baby_poisoning, container, false);

        stepsTextView = rootView.findViewById(R.id.text_baby_poisoning_steps);

        loadFirstAidGuide();

        return rootView;
    }

    private void loadFirstAidGuide() {
        ApiService apiService = RetrofitClient.getApiService();

        Call<FirstAidGuideViewModel> call = apiService.getFirstAidGuide(10);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<FirstAidGuideViewModel> call, @NonNull Response<FirstAidGuideViewModel> response) {
                if (response.isSuccessful()) {
                    FirstAidGuideViewModel guide = response.body();
                    assert guide != null;

                    stepsTextView.setText(guide.getText1());
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
