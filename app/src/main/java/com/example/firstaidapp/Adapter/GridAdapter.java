package com.example.firstaidapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.firstaidapp.R;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private CardView cardView;
    private final String[] titles = {"Allergies", "Burns", "CPR", "Bleeding","Broken Bone","Shock","Choking","Poisoning",};
    private final int[] icons = {
            R.drawable.allergies,
            R.drawable.burns,
            R.drawable.heart_attack,
            R.drawable.bleeding,
            R.drawable.broken_bone,
            R.drawable.shock,
            R.drawable.choking,
            R.drawable.poison
    };

    public GridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.icon);
        TextView textView = convertView.findViewById(R.id.title);

        cardView = convertView.findViewById(R.id.container);

        imageView.setImageResource(icons[position]);
        textView.setText(titles[position]);

        return convertView;
    }
}

