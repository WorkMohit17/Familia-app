package com.example.familia.Onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.familia.R;

public class Onboarding_Seventh_Fragment extends Fragment {

    private String hasChild = ""; // "yes" or "no"

    public Onboarding_Seventh_Fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding__seventh_, container, false);

        Button yesButton = view.findViewById(R.id.yesButton);
        Button noButton = view.findViewById(R.id.noButton);
        Button nextButton = view.findViewById(R.id.nextButton);

        // Retrieve previous bundle (from 6th fragment)


        // Click listeners for Yes/No buttons
        yesButton.setOnClickListener(v -> hasChild = "yes");
        noButton.setOnClickListener(v -> hasChild = "no");

        // Next button click → Store answer & move to 8th fragment
        nextButton.setOnClickListener(v -> {
            if (!hasChild.isEmpty()) {

                Bundle bundle = getArguments();
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putString("has_child", hasChild); // Add response to bundle

                replaceChildFragment(new Onboarding_Eight_Fragment(), bundle);
            } else {
                Toast.makeText(getContext(), "Please select an option", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    private void replaceChildFragment(Fragment fragment, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle); // Attach the bundle to the new fragment
        }
        getChildFragmentManager().beginTransaction()
                .replace(R.id.onBoarding_container_fragment, fragment)
                .commit();
    }
}
