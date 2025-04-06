package com.example.familia.GetStarted;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.familia.MainActivity;
import com.example.familia.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GetStartedScreen extends AppCompatActivity {

    CardView getStartedButton, getStartedCardView;
    LinearLayout getStartedIntro;
    private FirebaseAuth mAuth;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_screen);

        getStartedButton = findViewById(R.id.get_started_button);
        getStartedCardView = findViewById(R.id.get_started_cardview);
        getStartedIntro = findViewById(R.id.get_started_intro);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId(getString(R.string.default_web_client_id)) // Ensure this is set in strings.xml
                                .setFilterByAuthorizedAccounts(false)
                                .build())
                .build();

        getStartedButton.setOnClickListener(v -> showCard());
        getStartedCardView.setOnClickListener(v -> signInWithGoogle());
    }

    private void showCard() {
        getStartedButton.setVisibility(View.GONE);
        getStartedIntro.setVisibility(View.GONE);
        getStartedCardView.setVisibility(View.VISIBLE);
    }

    private void signInWithGoogle() {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, result -> {
                    try {
                        IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender()).build();
                        signInLauncher.launch(intentSenderRequest);
                    } catch (Exception e) {
                        Log.e("GoogleSignIn", "Error launching sign-in intent", e);
                    }
                })
                .addOnFailureListener(this, e -> Toast.makeText(this, "Google Sign-In Failed!", Toast.LENGTH_SHORT).show());
    }

    private final ActivityResultLauncher<IntentSenderRequest> signInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartIntentSenderForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    try {
                        SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                        String idToken = credential.getGoogleIdToken();
                        if (idToken != null) {
                            firebaseAuthWithGoogle(idToken);
                        }
                    } catch (ApiException e) {
                        Log.e("Google Sign-In", "Error: ", e);
                    }
                }
            });

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String name = user.getDisplayName();
                            String photoUrl = (user.getPhotoUrl() != null) ? user.getPhotoUrl().toString() : "";

                            Bundle bundle = new Bundle();
                            bundle.putString("user_name", name);
                            bundle.putString("user_photo", photoUrl);

                            Intent intent = new Intent(GetStartedScreen.this, MainActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
