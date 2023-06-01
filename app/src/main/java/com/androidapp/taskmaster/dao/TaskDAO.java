package com.androidapp.taskmaster.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.androidapp.taskmaster.models.Task;

import java.util.List;

@Dao
public interface TaskDAO {
    @Insert
    void insertTask(Task task);

    @Query("SELECT * FROM Task WHERE id = :id")
    Task findTaskById(Long id);

    //Find all
    @Query("SELECT * FROM Task")
    List<Task> findAllTasks();

    //Find all by type
//    @Query("SELECT * FROM Task WHERE state = :state")
//    List<Task> findAllTasksByState();

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);


}
