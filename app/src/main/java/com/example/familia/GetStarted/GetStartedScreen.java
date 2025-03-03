package com.example.familia.GetStarted;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.familia.R;

public class GetStartedScreen extends AppCompatActivity {

    CardView getStartedButton, getStartedCardView;
    LinearLayout getStartedIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_screen);

        getStartedButton = findViewById(R.id.get_started_button);
        getStartedCardView = findViewById(R.id.get_started_cardview);
        getStartedIntro = findViewById(R.id.get_started_intro);

        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCard();
            }
        });
    }

    void showCard(){
        getStartedButton.setVisibility(View.GONE);
        getStartedIntro.setVisibility(View.GONE);
        getStartedCardView.setVisibility(View.VISIBLE);
    }
}