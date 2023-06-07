package com.androidapp.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.androidapp.taskmaster.R;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "signUpActivity";

    public static final String SIGNUP_EMAIL_TAG = "SIGNUP_EMAIL_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void setUpSignUpButton() {
        Button signUpButton = findViewById(R.id.verifyAccountActivityVerificationButton);
        String userEmail = ((EditText) findViewById(R.id.signUpActivityEmailEditText)).getText().toString();
        String userPassword = ((EditText) findViewById(R.id.signUpActivityPasswordEditText)).getText().toString();
        signUpButton.setOnClickListener(v -> {
            Amplify.Auth.signUp(userEmail,
                    userPassword,
                    AuthSignUpOptions.builder()
                            .userAttribute(AuthUserAttributeKey.email(),userEmail)
//                            .userAttribute(AuthUserAttributeKey.nickname(),"Ryan")
                            .build(),
                    good -> {
                        Log.i(TAG, "signUp succeeded" + good.toString());
                    },
                    bad -> {
                        Log.i(TAG,"Signup failed: " + bad.toString());
                    }

            );
        });
    }
}