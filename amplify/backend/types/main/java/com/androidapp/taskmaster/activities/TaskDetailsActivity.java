package com.androidapp.taskmaster.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.taskmaster.MainActivity;
import com.androidapp.taskmaster.R;

public class TaskDetailsActivity extends AppCompatActivity {
    public static final String TASK_Title_TAG = "taskName";
    public static final String TASK_Body_TAG = "taskBody";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Intent intent = getIntent();
        String taskTitle = intent.getStringExtra(TaskDetailsActivity.TASK_Title_TAG);

        TextView taskDetailsTextView = findViewById(R.id.taskDetailsActivityInputName);
        taskDetailsTextView.setText(taskTitle);

        String taskBody = intent.getStringExtra(TaskDetailsActivity.TASK_Body_TAG);

        TextView taskDetailsBodyView = findViewById(R.id.taskDetailsTaskBodyTextView);
        taskDetailsBodyView.setText(taskBody);



    }

    protected void onResume () {
        super.onResume();

//        preferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);
//        String task = preferences.getString(TaskDetailsActivity.TASK_Title_TAG, "No task");
//        ((TextView) findViewById(R.id.taskDetailsActivityInputName)).setText(task);
    }
}
