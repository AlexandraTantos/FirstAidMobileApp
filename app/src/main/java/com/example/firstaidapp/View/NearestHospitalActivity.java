package com.example.firstaidapp.View;
import com.example.firstaidapp.Service.ApiService;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.firstaidapp.Service.RetrofitClient;
import com.example.firstaidapp.ViewModel.HospitalViewModel;
import com.example.firstaidapp.R;
import com.example.firstaidapp.ViewModel.UserLocationViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearestHospitalActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_hospital);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.custom_info_window, null);
                TextView title = view.findViewById(R.id.title);
                TextView address = view.findViewById(R.id.address);
                title.setText(marker.getTitle());
                if (marker.getTag() != null && marker.getTag() instanceof HospitalViewModel) {
                    HospitalViewModel hospital = (HospitalViewModel) marker.getTag();
                    address.setText(hospital.getAddress());
                }
                return view;
            }
        });
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                       LatLng userLocation = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));

                        getHospitals(latitude, longitude);
                    }
                });
    }

    private void getHospitals(double latitude, double longitude) {
        UserLocationViewModel userLocation = new UserLocationViewModel(String.valueOf(latitude), String.valueOf(longitude));

        ApiService apiService = RetrofitClient.getApiService();

        Call<List<HospitalViewModel>> call = apiService.getHospitals(userLocation);
        Log.d("Retrofit", "Sending request to: " + call.request().url());

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<HospitalViewModel>> call, @NonNull Response<List<HospitalViewModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HospitalViewModel> hospitals = response.body();

                    for (HospitalViewModel hospital : hospitals) {
                        LatLng hospitalLocation = new LatLng(hospital.getLocation().getLat(), hospital.getLocation().getLng());
                        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hospital_marker);
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 100, 100, false);
                        mMap.addMarker(new MarkerOptions().position(hospitalLocation).title(hospital.getName()).icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));
                    }
                } else {
                    Log.e("Retrofit", "Response error: " + response.code() + " - " + response.message());
                    Toast.makeText(NearestHospitalActivity.this, "No hospitals found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<HospitalViewModel>> call, @NonNull Throwable t) {
                Toast.makeText(NearestHospitalActivity.this, "Error loading hospitals", Toast.LENGTH_SHORT).show();
                Log.e("Retrofit", "Failed to load hospitals", t);
            }
        });
    }
}
