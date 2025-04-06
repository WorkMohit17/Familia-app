package com.example.familia.Onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.familia.R;
import org.json.JSONException;
import org.json.JSONObject;

public class Onboarding_Fifth_Fragment extends Fragment {

    private String selectedIncome = null; // Store selected income
    private JSONObject userDataJson; // Store the JSON object

    public Onboarding_Fifth_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding__fifth_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView cardBelow5L = view.findViewById(R.id.card_below_5l);
        CardView card5to10L = view.findViewById(R.id.card_5_to_10l);
        CardView card10to15L = view.findViewById(R.id.card_10_to_15l);
        CardView cardAbove15L = view.findViewById(R.id.card_above_15l);
        Button nextButton = view.findViewById(R.id.nextButton);

        RadioButton radioBelow5L = view.findViewById(R.id.radio_below_5l);
        RadioButton radio5to10L = view.findViewById(R.id.radio_5_to_10l);
        RadioButton radio10to15L = view.findViewById(R.id.radio_10_to_15l);
        RadioButton radioAbove15L = view.findViewById(R.id.radio_above_15l);

        // Retrieve the existing bundle JSON data
        Bundle bundle = getArguments();
        if (bundle != null) {
            String userDataString = bundle.getString("userData");
            try {
                userDataJson = new JSONObject(userDataString);
            } catch (JSONException e) {
                e.printStackTrace();
                userDataJson = new JSONObject(); // Create a new JSON object if parsing fails
            }
        } else {
            userDataJson = new JSONObject(); // Create new JSON if no data exists
        }

        // Set Click Listeners on Cards to select income
        cardBelow5L.setOnClickListener(v -> {
            selectedIncome = "Below 5L";
            radioBelow5L.setChecked(true);
            radio5to10L.setChecked(false);
            radio10to15L.setChecked(false);
            radioAbove15L.setChecked(false);
        });

        card5to10L.setOnClickListener(v -> {
            selectedIncome = "5L - 10L";
            radioBelow5L.setChecked(false);
            radio5to10L.setChecked(true);
            radio10to15L.setChecked(false);
            radioAbove15L.setChecked(false);
        });

        card10to15L.setOnClickListener(v -> {
            selectedIncome = "10L - 20L";
            radioBelow5L.setChecked(false);
            radio5to10L.setChecked(false);
            radio10to15L.setChecked(true);
            radioAbove15L.setChecked(false);
        });

        cardAbove15L.setOnClickListener(v -> {
            selectedIncome = "Above 20L";
            radioBelow5L.setChecked(false);
            radio5to10L.setChecked(false);
            radio10to15L.setChecked(false);
            radioAbove15L.setChecked(true);
        });

        // Next Button Click - Move to Sixth Fragment
        nextButton.setOnClickListener(v -> {
            if (selectedIncome != null) {
                try {
                    userDataJson.put("income", selectedIncome);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                bundle.putString("userData", userDataJson.toString());

                replaceChildFragment(new Onboarding_Sixth_Fragment(), bundle);
            }
        });
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
