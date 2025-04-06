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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.familia.R;
import org.json.JSONException;
import org.json.JSONObject;

public class Onboarding_Fourth_Fragment extends Fragment {

    private String selectedProfession = null; // Store selected profession

    public Onboarding_Fourth_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding__fourth_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView cardDoctor = view.findViewById(R.id.card_doctor);
        CardView cardEngineer = view.findViewById(R.id.card_engineer);
        CardView cardTeacher = view.findViewById(R.id.card_teacher);
        CardView cardBusiness = view.findViewById(R.id.card_business);
        Button nextButton = view.findViewById(R.id.nextButton);

        RadioButton radioDoctor = view.findViewById(R.id.radio_doctor);
        RadioButton radioEngineer = view.findViewById(R.id.radio_engineer);
        RadioButton radioTeacher = view.findViewById(R.id.radio_teacher);
        RadioButton radioBusiness = view.findViewById(R.id.radio_business);

        // Set Click Listeners on Cards to select profession
        cardDoctor.setOnClickListener(v -> {
            selectedProfession = "Doctor";
            radioDoctor.setChecked(true);
            radioEngineer.setChecked(false);
            radioTeacher.setChecked(false);
            radioBusiness.setChecked(false);
        });

        cardEngineer.setOnClickListener(v -> {
            selectedProfession = "Engineer";
            radioDoctor.setChecked(false);
            radioEngineer.setChecked(true);
            radioTeacher.setChecked(false);
            radioBusiness.setChecked(false);
        });

        cardTeacher.setOnClickListener(v -> {
            selectedProfession = "Teacher";
            radioDoctor.setChecked(false);
            radioEngineer.setChecked(false);
            radioTeacher.setChecked(true);
            radioBusiness.setChecked(false);
        });

        cardBusiness.setOnClickListener(v -> {
            selectedProfession = "Artist";
            radioDoctor.setChecked(false);
            radioEngineer.setChecked(false);
            radioTeacher.setChecked(false);
            radioBusiness.setChecked(true);
        });

        // Next Button Click - Move to Fifth Fragment
        nextButton.setOnClickListener(v -> {
            if (selectedProfession != null) {
                JSONObject professionJson = new JSONObject();
                try {
                    professionJson.put("profession", selectedProfession);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Bundle bundle = new Bundle();
                bundle.putString("userData", professionJson.toString());

                replaceChildFragment(new Onboarding_Fifth_Fragment(), bundle);
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
