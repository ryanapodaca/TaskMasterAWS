package com.androidapp.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.androidapp.taskmaster.activities.AddTaskActivity;
import com.androidapp.taskmaster.activities.AllTasksActivity;
import com.androidapp.taskmaster.activities.LoginActivity;
import com.androidapp.taskmaster.activities.SettingsActivity;
import com.androidapp.taskmaster.adapters.TasksRecycleViewAdapter;
//import com.androidapp.taskmaster.models.Task;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    public static final String TASK_NAME_TAG = "taskName";

    public static final String DATA_BASE_NAME = "task_master_database";
    List<Task> tasks;
    TasksRecycleViewAdapter adapter;
    SharedPreferences preferences;

    AuthUser authUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Steps for functionality
        //1. Get UI by ID
        //2. Add event listener to element
        //3. Attach fallback function to onClick method
        // 4. callback logic

//        Team team1 = Team.builder()
//                .title("team1")
//                .build();
//
//        Team team2 = Team.builder()
//                .title("team2")
//                .build();
//
//        Team team3 = Team.builder()
//                .title("team3")
//                .build();
//
//        Amplify.API.mutate(
//                ModelMutation.create(team1),
//                success -> {},
//                failure -> {}
//        );
//        Amplify.API.mutate(
//                ModelMutation.create(team2),
//                success -> {},
//                failure -> {}
//        );
//        Amplify.API.mutate(
//                ModelMutation.create(team3),
//                success -> {},
//                failure -> {}
//        );


        //TODO: SETUP DB QUERY
//        tasks = taskMasterDatabase.taskDAO().findAllTasks();
        tasks = new ArrayList<>();


        setUpAddTaskButton();
        setUpAllTasksButton();
        setUpSettingsButton();
        setUpRecycleView();
        setUpLoginButton();
        setUpLogoutButton();

    }


    protected void onResume () {
        super.onResume();
        checkForAuthUser();
        tasks.clear();
        //TODO:SETUP DB QUERY
//        tasks.addAll(taskMasterDatabase.taskDAO().findAllTasks());
        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    Log.i(TAG, "Read Tasks successfully");
                    tasks.clear();
                    tasks = new ArrayList<>();
//                    for (Task databaseTask : success.getData()){
//                        String teamName = "team1";
//                        if(databaseTask.getTeamTitle().getTitle().equals(teamName)){
//                            tasks.add(databaseTask);
//                        }
//                    }
//                    runOnUiThread(adapter::notifyDataSetChanged);
                },
                failure -> Log.i(TAG,"Did not read tasks successfully")
        );


        preferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);
        String userName = preferences.getString(SettingsActivity.USER_NAME_TAG, "No user name");
        ((TextView) findViewById(R.id.mainActivityUserNameView)).setText(userName + "'s tasks");
        String teamName = preferences.getString(SettingsActivity.TEAM_TAG, "No Team");
        ((TextView) findViewById(R.id.mainActivityTeamNameTextView)).setText(teamName);
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

    public void setUpLoginButton() {
        Button loginButton = findViewById(R.id.mainActivityLoginButton);
        loginButton.setOnClickListener(v -> {
            Intent goToLoginActivity = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(goToLoginActivity);
        });
    }

    public void setUpLogoutButton() {
        Button logoutButton = findViewById(R.id.mainActivityLogoutButton);
        logoutButton.setOnClickListener(v -> {
            AuthSignOutOptions options = AuthSignOutOptions.builder()
                    .globalSignOut(true)
                    .build();

            Amplify.Auth.signOut(options, signOutResult -> {
                if (signOutResult instanceof AWSCognitoAuthSignOutResult.CompleteSignOut) {
                    Log.i(TAG,"Global logout successful!");
                } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.PartialSignOut) {
                    Log.i(TAG,"Partial logout successful!");
                } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.FailedSignOut) {
                    Log.i(TAG,"Logout failed: " + signOutResult.toString());
                }
            });
        });
    }


    public void checkForAuthUser() {
        Amplify.Auth.getCurrentUser(
                success -> {
                    Log.i(TAG, "User authenticated with username: " + success.getUsername());
                    authUser = success;
                    runOnUiThread(this::renderButtons);
                },
                failure -> {
                    Log.i(TAG, "There is no current authenticated user");
                    authUser = null;
                    runOnUiThread(this::renderButtons);
                }
        );
    }

    public void renderButtons() {
        if (authUser == null) {
            Button loginButton = findViewById(R.id.mainActivityLoginButton);
            loginButton.setVisibility(View.VISIBLE);
            Button logoutButton = findViewById(R.id.mainActivityLogoutButton);
            logoutButton.setVisibility(View.INVISIBLE);
        } else {
            Button loginButton = findViewById(R.id.mainActivityLoginButton);
            loginButton.setVisibility(View.INVISIBLE);
            Button logoutButton = findViewById(R.id.mainActivityLogoutButton);
            logoutButton.setVisibility(View.VISIBLE);
        }
    }

}