package com.example.familia.Onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.familia.HomeActivity;
import com.example.familia.R;

public class Onboarding_Eight_Fragment extends Fragment {

    private EditText editTextAddress, editTextPincode, editTextTown, editTextCity, editTextState, editTextCountry;
    private Button btnProceed;
    private Bundle receivedBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding__eight_, container, false);

        // Initialize views
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextPincode = view.findViewById(R.id.editTextPincode);
        editTextTown = view.findViewById(R.id.editTextTown);
        editTextCity = view.findViewById(R.id.editTextCity);
        editTextState = view.findViewById(R.id.editTextState);
        editTextCountry = view.findViewById(R.id.editTextCountry);
        btnProceed = view.findViewById(R.id.btnProceed);

        // Get the bundle from the previous fragment if it exists
        receivedBundle = getArguments();
        if (receivedBundle == null) {
            receivedBundle = new Bundle(); // fallback to empty bundle
        }

        btnProceed.setOnClickListener(v -> {
            String address = editTextAddress.getText().toString().trim();
            String pincode = editTextPincode.getText().toString().trim();
            String town = editTextTown.getText().toString().trim();
            String city = editTextCity.getText().toString().trim();
            String state = editTextState.getText().toString().trim();
            String country = editTextCountry.getText().toString().trim();

            if (address.isEmpty() || pincode.isEmpty() || town.isEmpty() || city.isEmpty() || state.isEmpty() || country.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all address fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Optional: save address data into bundle
            String fullLocation = address + ", " + town + ", " + city + ", " + state + ", " + country + " - " + pincode;
            receivedBundle.putString("location", fullLocation);

            // Navigate to home screen
            navigateToHome();
        });

        return view;
    }

    private void navigateToHome() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        requireActivity().finish(); // Optional: closes onboarding flow
    }
}
