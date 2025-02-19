package com.example.firstaidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BabyGridAdapter extends BaseAdapter {
    private Context context;
    private final String[] titles = {"CPR", "Poisoning", "Fever","Choking",};
    private final int[] icons = {
            R.drawable.baby_cpr,
            R.drawable.baby_poisoning,
            R.drawable.baby_fever,
            R.drawable.baby_choking
    };

    public BabyGridAdapter(Context context) {
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

        imageView.setImageResource(icons[position]);
        textView.setText(titles[position]);

        return convertView;
    }
}

