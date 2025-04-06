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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.familia.R;

public class Onboarding_Third_Fragment extends Fragment {

    public Onboarding_Third_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding__third_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView readyButton = view.findViewById(R.id.ready_btn); // Ensure your button ID is correct

        readyButton.setOnClickListener(v -> {
            replaceChildFragment(new Onboarding_Fourth_Fragment());
        });
    }

    private void replaceChildFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.onBoarding_container_fragment, fragment)
                .commit();
    }
}
