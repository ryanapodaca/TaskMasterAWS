package com.androidapp.taskmaster.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.taskmaster.MainActivity;
import com.androidapp.taskmaster.R;
import com.androidapp.taskmaster.activities.TaskDetailsActivity;
import com.androidapp.taskmaster.models.Task;

import java.util.List;

public class TasksRecycleViewAdapter extends RecyclerView.Adapter<TasksRecycleViewAdapter.TasksListViewHolder> {

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
        String taskFragmentText = position + "." + taskTitle;
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
