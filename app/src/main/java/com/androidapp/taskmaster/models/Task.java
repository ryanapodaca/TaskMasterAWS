package com.androidapp.taskmaster.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true) public Long id;

    String title;
    String body;

    public Date date;
    TaskState state;

    public Task(String title, String body, Date date, TaskState state) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public enum TaskState {
        NEW("new"),
        ASSIGNED("assigned"),
        INPROGRESS("in progress"),
        COMPLETE("complete");

        private final String taskState;


        TaskState(String taskState) {
            this.taskState = taskState;
        }
        //Method from string
        public static TaskState fromString(String possibleTaskState) {
            for (TaskState state : TaskState.values()) {
                if (state.taskState.equals(possibleTaskState))
                    return state;
            }
            return null;
        }

        @Override
        public String toString() {
            return "TaskState{" +
                    "taskState='" + taskState + '\'' +
                    '}';
        }
    }

}
