package com.androidapp.taskmaster.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.taskmaster.R;

public class AllTasksActivity extends AppCompatActivity {
    private final String TAG = "AddTaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

    }
}
