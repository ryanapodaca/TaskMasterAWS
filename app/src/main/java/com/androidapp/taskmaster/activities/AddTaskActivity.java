package com.androidapp.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidapp.taskmaster.MainActivity;
import com.androidapp.taskmaster.R;
import com.androidapp.taskmaster.database.TaskMasterDatabase;
import com.androidapp.taskmaster.models.Task;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    private final String TAG = "AddTaskActivity";
    TaskMasterDatabase taskMasterDatabase;
    Spinner taskStateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        //Steps for functionality
        //1. Get UI by ID
        //2. Add event listener to element
        //3. Attach fallback function to onClick method
        // 4. callback logic

        taskStateSpinner = findViewById(R.id.addTaskSpinner);

        taskMasterDatabase = Room.databaseBuilder(
                getApplicationContext(),
                TaskMasterDatabase.class,
                MainActivity.DATA_BASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        setUpTaskStateSpinner();
        setUpSaveButton();
//        Button submitButton = (Button) findViewById(R.id.addTaskActivitySubmitButton);
//
//        submitButton.setOnClickListener(v -> {
//            Log.v(TAG, "Submit clicked");
//
//            TextView addTaskSubmitted = ((TextView) findViewById(R.id.submitText));
//            addTaskSubmitted.setText(R.string.submitted_confirmation);
//
//            Toast.makeText(AddTaskActivity.this, "Task saved!", Toast.LENGTH_SHORT).show();
//        });

    }

    public void setUpTaskStateSpinner() {
        taskStateSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Task.TaskState.values()
        ));
    }

    public void setUpSaveButton() {
        // set listener
        findViewById(R.id.addTaskActivitySubmitButton).setOnClickListener(v -> {
            System.out.println("hello");
            Task newTask = new Task(
                    ((EditText)findViewById(R.id.addTaskTitleTextInput)).getText().toString(),
                    ((EditText)findViewById(R.id.addTaskDescriptionMultiLine)).getText().toString(),
                    new Date(),
                    Task.TaskState.fromString(taskStateSpinner.getSelectedItem().toString())
            );
            taskMasterDatabase.taskDAO().insertTask(newTask);
            Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show();
        });
    }


}
