package com.androidapp.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.androidapp.taskmaster.MainActivity;
import com.androidapp.taskmaster.R;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "Login_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpLoginButton();
        setUpSignUpButton();

    }

    public void setUpLoginButton() {
        Intent callingIntent = getIntent();
        String userEmail = callingIntent.getStringExtra(VerifyAccountActivity.VERIFICATION_EMAIL_TAG);
        EditText userEmailEditText = findViewById(R.id.loginActivityUsernameEditText);
        if(userEmail != null) {
            userEmailEditText.setText(userEmail);
        }
        EditText userPasswordEditText = findViewById(R.id.loginActivityPasswordEditText);
        Button loginButton = findViewById(R.id.loginActivityLoginButton);

        loginButton.setOnClickListener(v -> {
            Amplify.Auth.signIn(userEmailEditText.getText().toString(),
                    userPasswordEditText.getText().toString(),
                    success -> {
                        Log.i(TAG, "Login succeeded: " + success.toString());
                        Intent goToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(goToMainActivity);
                    },
                    failure -> {
                        Log.i(TAG, "Login failed: " + failure.toString());
                    });
        });
    }

    public void setUpSignUpButton() {
        Button signUpButton = findViewById(R.id.loginActivitySignUpButton);

        signUpButton.setOnClickListener(v -> {
            Intent goToSignUpActivity = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(goToSignUpActivity);
        });
    }
}