package com.example.firstaidapp.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstaidapp.Service.ApiService;
import com.example.firstaidapp.Service.RetrofitClient;
import com.example.firstaidapp.R;
import com.example.firstaidapp.Model.Contact;
import com.example.firstaidapp.Adapter.ContactAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmergencyContactsFragment extends Fragment {
    private RecyclerView recyclerView;

    public EmergencyContactsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emergency_contacts, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadEmergencyContacts();

        Button addContactButton = view.findViewById(R.id.btn_add_contact);
        addContactButton.setOnClickListener(v -> showAddContactDialog());

        return view;
    }

    private void showAddContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Emergency Contact");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_contact, null);
        EditText nameInput = dialogView.findViewById(R.id.edit_text_name);
        EditText phoneInput = dialogView.findViewById(R.id.edit_text_phone);
        builder.setView(dialogView);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = nameInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(getContext(), "Please enter name and phone", Toast.LENGTH_SHORT).show();
                return;
            }

            int userId = Integer.parseInt(getUserIdFromSession());

            Contact newContact = new Contact(name, phone,userId);
            newContact.setUserId(userId);

            ApiService apiService = RetrofitClient.getApiService();
            Call<Void> call = apiService.addEmergencyContact(userId, newContact);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Contact added successfully", Toast.LENGTH_SHORT).show();
                        loadEmergencyContacts();
                    } else {
                        try {
                            String error = response.errorBody().string();
                            Log.e("AddContactError", "Error: " + response.code() + " - " + error);
                            Toast.makeText(getContext(), "Failed: " + response.code(), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e("AddContactError", "Exception reading error body", e);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Log.e("AddContactError", "Network failure", t);
                    Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    public void loadEmergencyContacts() {
        String idFromSession = getUserIdFromSession();
        int id = Integer.parseInt(idFromSession);
        ApiService apiService = RetrofitClient.getApiService();

        Call<List<Contact>> call = apiService.getEmergencyContacts(id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<Contact>> call, @NonNull Response<List<Contact>> response) {
                if (response.isSuccessful()) {
                    List<Contact> contacts = response.body();
                    ContactAdapter contactAdapter = new ContactAdapter(contacts);
                    recyclerView.setAdapter(contactAdapter);
                } else {
                    if (response.code() == 404) {
                        Toast.makeText(getActivity(), "No emergency contacts found", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Failed to load emergency contacts", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getUserIdFromSession() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userId", "");
    }
}
