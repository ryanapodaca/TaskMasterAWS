package com.androidapp.taskmaster.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.androidapp.taskmaster.dao.TaskDAO;
import com.androidapp.taskmaster.models.Task;

@TypeConverters({TaskMasterDatabaseConverters.class})
@Database(entities = {Task.class}, version = 1)
public abstract class TaskMasterDatabase extends RoomDatabase {
    public abstract TaskDAO taskDAO();
}
