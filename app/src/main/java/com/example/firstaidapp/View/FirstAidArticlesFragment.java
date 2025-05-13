package com.example.firstaidapp.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.firstaidapp.R;

public class FirstAidArticlesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first_aid_articles, container, false);

        Button btnArticle1 = view.findViewById(R.id.btn_article_1);
        Button btnArticle2 = view.findViewById(R.id.btn_article_2);
        Button btnArticle3 = view.findViewById(R.id.btn_article_3);
        Button btnArticle4 = view.findViewById(R.id.btn_article_4);
        Button btnArticle5 = view.findViewById(R.id.btn_article_5);
        Button btnArticle6 = view.findViewById(R.id.btn_article_6);

        btnArticle1.setOnClickListener(v -> openArticle("https://pubmed.ncbi.nlm.nih.gov/21658557/"));
        btnArticle2.setOnClickListener(v -> openArticle("https://pubmed.ncbi.nlm.nih.gov/25630149/"));
        btnArticle3.setOnClickListener(v -> openArticle("https://pubmed.ncbi.nlm.nih.gov/30285362/"));
        btnArticle4.setOnClickListener(v -> openArticle("https://pubmed.ncbi.nlm.nih.gov/16951621/"));
        btnArticle5.setOnClickListener(v -> openArticle("https://www.sja.org.uk/get-advice/first-aid-advice/minor-illnesses-and-injuries/low-voltage-electrocution/"));
        btnArticle6.setOnClickListener(v -> openArticle("https://www.mayoclinic.org/first-aid/first-aid-fractures/basics/art-20056641"));

        return view;
    }

    private void openArticle(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
