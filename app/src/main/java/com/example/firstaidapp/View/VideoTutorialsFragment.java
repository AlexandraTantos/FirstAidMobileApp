package com.example.firstaidapp.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.firstaidapp.R;

public class VideoTutorialsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_tutorials, container, false);

        Button btnVideo1 = view.findViewById(R.id.btn_video_1);
        Button btnVideo2 = view.findViewById(R.id.btn_video_2);

        btnVideo1.setOnClickListener(v -> openVideo("https://www.youtube.com/watch?v=hizBdM1Ob68"));
        btnVideo2.setOnClickListener(v -> openVideo("https://www.youtube.com/watch?v=SqpcTF2HFvg"));

        return view;
    }

    private void openVideo(String videoUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        startActivity(intent);
    }
}
