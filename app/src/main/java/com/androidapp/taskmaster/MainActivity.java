package com.androidapp.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androidapp.taskmaster.activities.AddTaskActivity;
import com.androidapp.taskmaster.activities.AllTasksActivity;
import com.androidapp.taskmaster.activities.SettingsActivity;
import com.androidapp.taskmaster.activities.TaskDetailsActivity;
import com.androidapp.taskmaster.adapters.TasksRecycleViewAdapter;
import com.androidapp.taskmaster.database.TaskMasterDatabase;
import com.androidapp.taskmaster.models.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TASK_NAME_TAG = "taskName";
    TaskMasterDatabase taskMasterDatabase;
    public static final String DATA_BASE_NAME = "task_master_database";
    List<Task> tasks;
    TasksRecycleViewAdapter adapter;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Steps for functionality
        //1. Get UI by ID
        //2. Add event listener to element
        //3. Attach fallback function to onClick method
        // 4. callback logic

        taskMasterDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        TaskMasterDatabase.class,
                        DATA_BASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        tasks = taskMasterDatabase.taskDAO().findAllTasks();

//        List<Task> tasks = new ArrayList<>();
//        tasks.add(new Task("dishes"));
//        tasks.add(new Task("walk dog"));
//        tasks.add(new Task("trash"));
//        tasks.add(new Task("errands"));
//        tasks.add(new Task("laundry"));


        setUpAddTaskButton();
        setUpAllTasksButton();
        setUpSettingsButton();
        setUpRecycleView();

    }


    protected void onResume () {
        super.onResume();
        tasks.clear();
        tasks.addAll(taskMasterDatabase.taskDAO().findAllTasks());
//        adapter.notifyDataSetChanged();


        preferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);
        String userName = preferences.getString(SettingsActivity.USER_NAME_TAG, "No user name");
        ((TextView) findViewById(R.id.mainActivityUserNameView)).setText(userName + "'s tasks");
    }

    public void setUpAddTaskButton () {
        Button goToAddTasksButton = (Button) findViewById(R.id.addTaskButton);
        goToAddTasksButton.setOnClickListener(v -> {
            Intent goToAddTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(goToAddTaskIntent);
        });
    }

    public void setUpAllTasksButton () {
        Button goToAllTasksButton = (Button) findViewById(R.id.allTasksButton);
        goToAllTasksButton.setOnClickListener(v -> {
            Intent goToAllTasksIntent = new Intent(MainActivity.this, AllTasksActivity.class);
            startActivity(goToAllTasksIntent);
        });
    }

    public void setUpSettingsButton () {
        ImageButton gotToSettingsButton = (ImageButton) findViewById(R.id.mainActivitySettingsButton);
        gotToSettingsButton.setOnClickListener(v -> {
            Intent goToSettingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(goToSettingsIntent);
        });
    }




    public void setUpRecycleView () {
        RecyclerView tasksRecycleView = (RecyclerView) findViewById(R.id.mainActivityTasksListRecycleView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        // For later, if you want a horizontal list:
        //((LinearLayoutManager)layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);

        tasksRecycleView.setLayoutManager(layoutManager);

        //Set adapter
        TasksRecycleViewAdapter adapter = new TasksRecycleViewAdapter(tasks, this);
        tasksRecycleView.setAdapter(adapter);


    }

}