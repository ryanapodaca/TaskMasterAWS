package com.androidapp.taskmaster.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Task;
import com.androidapp.taskmaster.MainActivity;
import com.androidapp.taskmaster.R;
import com.androidapp.taskmaster.activities.TaskDetailsActivity;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TasksRecycleViewAdapter extends RecyclerView.Adapter<TasksRecycleViewAdapter.TasksListViewHolder> {
    public static final String TAG = "TasksRecycleViewAdapter";

    List<Task> tasks;
    Context callingActivity;

    public TasksRecycleViewAdapter(List<Task> tasks, Context callingActivity) {
        this.tasks = tasks;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public TasksListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskFragment = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task_list, parent, false);
        return new TasksListViewHolder(taskFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksListViewHolder holder, int position) {
        TextView taskFragmentTextView = holder.itemView.findViewById(R.id.taskfragmentTextView);
        String taskTitle = tasks.get(position).getTitle();
        String taskBody = tasks.get(position).getBody();

        // Add date created
        DateFormat dateCreatedIs8601InputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        dateCreatedIs8601InputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateFormat dateCreatedOutputFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        dateCreatedOutputFormat.setTimeZone(TimeZone.getDefault());
        String dateCreatedCreateString = "";

        try{
            Date dateCreatedJavaDate = dateCreatedIs8601InputFormat.parse(tasks.get(position).getDate().format());
            if(dateCreatedJavaDate != null) {
                dateCreatedCreateString = dateCreatedOutputFormat.format(dateCreatedJavaDate);
            }
        } catch (ParseException e) {
            Log.e(TAG, "failed to parse and format data with stack trace: " +  e);
            e.printStackTrace();
        }

        String taskFragmentText = position + "." + taskTitle + " Date Created: " + dateCreatedIs8601InputFormat;
        taskFragmentTextView.setText(taskFragmentText);

        //Create on-click listener
        taskFragmentTextView.setOnClickListener(v -> {
            Intent goToTaskDetailsIntent = new Intent(callingActivity, TaskDetailsActivity.class);
            goToTaskDetailsIntent.putExtra(TaskDetailsActivity.TASK_Title_TAG, taskTitle);
            goToTaskDetailsIntent.putExtra(TaskDetailsActivity.TASK_Body_TAG, taskBody);
            callingActivity.startActivity(goToTaskDetailsIntent);

        });

    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TasksListViewHolder extends RecyclerView.ViewHolder {
        public TasksListViewHolder(View fragmentTaskView) {
            super(fragmentTaskView);
        }
    }
}
