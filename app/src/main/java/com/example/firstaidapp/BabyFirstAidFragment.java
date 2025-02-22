package com.example.firstaidapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class BabyFirstAidFragment extends Fragment {

    public BabyFirstAidFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_baby_first_aid, container, false);


        GridView gridView = rootView.findViewById(R.id.gridView);
        BabyGridAdapter adapter = new BabyGridAdapter(requireContext());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment selectedFragment = null;

                switch (position) {
                    case 0:
                        selectedFragment = new BabyCPRFragment();
                        break;
                    case 1:
                        selectedFragment = new BabyPoisoningFragment();
                        break;
                    case 2:
                        selectedFragment = new BabyFeverFragment();
                        break;
                    case 3:
                        selectedFragment = new BabyChokingFragment();
                        break;
                }

                if (selectedFragment != null) {
                    gridView.setVisibility(View.GONE);
                    rootView.findViewById(R.id.img_guide).setVisibility(View.GONE);

                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        return rootView;
    }
}