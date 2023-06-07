package com.androidapp.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.TaskStateEnum;
import com.amplifyframework.datastore.generated.model.Team;
import com.androidapp.taskmaster.MainActivity;
import com.androidapp.taskmaster.R;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddTaskActivity extends AppCompatActivity {
    private final String TAG = "AddTaskActivity";

    Spinner taskStateSpinner;
    Spinner taskTeamSpinner = null;

    CompletableFuture<List<Team>> teamFuture = null;
    ArrayList<String> teamTitles;
    ArrayList<Team> teams;

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
        taskTeamSpinner = findViewById(R.id.addTaskTeamSpinner);
        teamTitles = new ArrayList<>();
        teams = new ArrayList<>();

        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(TAG,"Read teams successfully");
                    for (Team databaseTeam : success.getData()){
                        teamTitles.add(databaseTeam.getTitle());
                        teams.add(databaseTeam);
                    }
                    teamFuture.complete(teams);
                    runOnUiThread(this::setUpAddTaskSpinners);

                },
                failure -> {
                    teamFuture.complete(null);
                    Log.e(TAG, "Failed to read teams" + failure);
                }
        );



//        setUpAddTaskSpinners();
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

    public void setUpAddTaskSpinners() {
        taskStateSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
//                Collections.singletonList(Task.STATE)  not sur if this method will work for spinner.
                TaskStateEnum.values()
        ));
        taskTeamSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                teamTitles
        ));
    }


    //TODO: Fix database save
    public void setUpSaveButton() {
        // set listener
        findViewById(R.id.addTaskActivitySubmitButton).setOnClickListener(v -> {
            String taskTitle = ((EditText)findViewById(R.id.addTaskTitleTextInput)).getText().toString();
            String taskBody = ((EditText)findViewById(R.id.addTaskDescriptionMultiLine)).getText().toString();
            String selectedTeamString = taskTeamSpinner.getSelectedItem().toString();
//            Task newTask = new Task(
//                    ((EditText)findViewById(R.id.addTaskTitleTextInput)).getText().toString(),
//                    ((EditText)findViewById(R.id.addTaskDescriptionMultiLine)).getText().toString(),
//                    new Date(),
//                    Task.TaskState.fromString(taskStateSpinner.getSelectedItem().toString())
//            );
            List<Team> teams = null;
            try {
                teams = teamFuture.get();
            } catch (InterruptedException ie) {
                Log.e(TAG,"Interrupted exception while getting teams");
            } catch (ExecutionException ee){
                Log.e(TAG, "Execution exception while getting teams");
            }
            Team selectedTeam = teams.stream().filter(c -> c.getTitle().equals(selectedTeamString)).findAny().orElseThrow(RuntimeException::new);
            Task newTask = Task.builder()
                    .title(taskTitle)
                    .body(taskBody)
                    .date(new Temporal.DateTime(new Date(), 0))
                    .state((TaskStateEnum) taskStateSpinner.getSelectedItem())
                    .teamTitle(selectedTeam)
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(newTask),
                    successResponse -> Log.i(TAG, "AddTaskActivity.onCreate().setUpSaveButton(): made new task successfully."),
                    errorResponse -> Log.i(TAG, "AddTaskActivity.onCreate().setUpSaveButton(): create new task failed.")
            );


//            taskMasterDatabase.taskDAO().insertTask(newTask);
            Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show();
        });
    }


}
