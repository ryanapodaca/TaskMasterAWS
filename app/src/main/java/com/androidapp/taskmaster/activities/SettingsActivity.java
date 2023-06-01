package com.androidapp.taskmaster.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.taskmaster.R;

public class SettingsActivity extends AppCompatActivity {

    public static final String USER_NAME_TAG = "userName";

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        preferences = PreferenceManager.getDefaultSharedPreferences(this);  deprecated
        preferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);

        setUpSubmitButton(preferences);

    }

    public void setUpSubmitButton(SharedPreferences preferences) {
        Button submitButton = findViewById(R.id.settingsActivitySubmitButton);
        submitButton.setOnClickListener(v -> {
            SharedPreferences.Editor preferenceEditor = preferences.edit();

            EditText userNameEditText = findViewById(R.id.settingsActivityEditTextField);
            String userNameString = userNameEditText.getText().toString();

            preferenceEditor.putString(USER_NAME_TAG, userNameString);
            preferenceEditor.apply();

            Toast.makeText(SettingsActivity.this, "Setting saved!", Toast.LENGTH_SHORT).show();
        });
    }
}
