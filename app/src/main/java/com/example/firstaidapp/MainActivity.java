package com.example.firstaidapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Button btnCall = findViewById(R.id.btn_ic_call);
        btnCall.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Emergency Call Confirmation")
                    .setMessage("Are you sure you want to call emergency services (112)?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:112"));
                        startActivity(callIntent);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        gridView = findViewById(R.id.gridView);

        GridAdapter adapter = new GridAdapter(this);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment selectedFragment = null;

                switch (position) {
                    case 0:
                        selectedFragment = new AllergiesFragment();
                        break;
                    case 1:
                        selectedFragment = new BurnsFragment();
                        break;
                    case 2:
                        selectedFragment = new HeartAttackFragment();
                        break;
                    case 3:
                        selectedFragment = new BleedingFragment();
                        break;
                    case 4:
                        selectedFragment = new BrokenBoneFragment();
                        break;
                    case 5:
                        selectedFragment = new ShockFragment();
                        break;
                    case 6:
                        selectedFragment = new ChokingFragment();
                        break;
                    case 7:
                        selectedFragment = new PoisoningFragment();
                        break;
                }

                if (selectedFragment != null) {
                    gridView.setVisibility(View.GONE);
                    findViewById(R.id.img_guide).setVisibility(View.GONE);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });


        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            if(item.getItemId() == R.id.nav_guides_kids){
                fragment = new BabyFirstAidFragment();
                gridView.setVisibility(View.GONE);
                ImageView img = findViewById(R.id.img_guide);
                img.setVisibility(View.GONE);
            }
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commitAllowingStateLoss();
            }

            drawerLayout.closeDrawers();
            return true;
        });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            if (item.getItemId() == R.id.nav_profile) {
                fragment = new ProfileFragment();
                gridView.setVisibility(View.GONE);
                ImageView img = findViewById(R.id.img_guide);
                img.setVisibility(View.GONE);
            }else if(item.getItemId() == R.id.nav_home){
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else if(item.getItemId() == R.id.nav_settings){
                fragment = new SettingsFragment();
                gridView.setVisibility(View.GONE);
                ImageView img = findViewById(R.id.img_guide);
                img.setVisibility(View.GONE);
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commitAllowingStateLoss();
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }
}
