package com.example.familia.Onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.familia.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Onboarding_Sixth_Fragment extends Fragment {

    private CardView cardYes, cardNo;
    private Button nextButton;
    private String selectedHealthIssue = null;
    private JSONObject userData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding__sixth_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardYes = view.findViewById(R.id.cardYes);
        cardNo = view.findViewById(R.id.cardNo);
        nextButton = view.findViewById(R.id.buttonNext);

        // Retrieve the existing bundle
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("userData")) {
            try {
                userData = new JSONObject(bundle.getString("userData"));
            } catch (JSONException e) {
                e.printStackTrace();
                userData = new JSONObject();
            }
        } else {
            userData = new JSONObject();
        }

        cardYes.setOnClickListener(v -> {
            selectedHealthIssue = "Yes";
            highlightSelected(cardYes, cardNo);
        });

        cardNo.setOnClickListener(v -> {
            selectedHealthIssue = "No";
            highlightSelected(cardNo, cardYes);
        });

        nextButton.setOnClickListener(v -> {
            if (selectedHealthIssue != null) {
                try {
                    userData.put("healthIssue", selectedHealthIssue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Pass updated bundle to the next fragment

                bundle.putString("userData", userData.toString());

                replaceChildFragment(new Onboarding_Seventh_Fragment(), bundle);
            }
        });
    }

    private void highlightSelected(CardView selected, CardView other) {
        selected.setCardBackgroundColor(getResources().getColor(R.color.selected_card));
        other.setCardBackgroundColor(getResources().getColor(R.color.default_card));
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
