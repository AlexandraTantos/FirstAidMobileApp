package com.example.firstaidapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return rootView;
    }
}