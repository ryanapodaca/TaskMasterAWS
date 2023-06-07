package com.androidapp.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.androidapp.taskmaster.R;

public class VerifyAccountActivity extends AppCompatActivity {
    public static final String TAG = "VerifyAccountActivity";
    public static final String VERIFICATION_EMAIL_TAG = "VERIFICATION_EMAIL_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);

        setUpVerificationButton();
    }

    public void setUpVerificationButton() {
        Button verificationButton = findViewById(R.id.verifyAccountActivityVerificationButton);
        // need to grab email from calling intent
        Intent callingIntent = getIntent();
        String userEmail = callingIntent.getStringExtra(SignUpActivity.SIGNUP_EMAIL_TAG);
        EditText emailEditText = ((EditText) findViewById(R.id.verifyAccountActivityUsernameEditText));
        emailEditText.setText(userEmail);

        verificationButton.setOnClickListener(v -> {
            String verificationNumber = ((EditText) findViewById(R.id.verifyAccountActivityVerificationNumberEditText)).getText().toString();

            Amplify.Auth.confirmSignUp(userEmail,
                    verificationNumber,
                    success -> {
                        Log.i(TAG, "Verification succeeded: " + success.toString());
                        // make intent to move to login page and pass the email
                        Intent goToLoginActivity = new Intent(VerifyAccountActivity.this, LoginActivity.class);
                        goToLoginActivity.putExtra(VERIFICATION_EMAIL_TAG, userEmail);
                        startActivity(goToLoginActivity);
                    },
                    failure -> {
                        Log.i(TAG, "Verification failed: " + failure.toString());
                    }
            );
        });
    }
}