package com.cleanup.todoc_Etienne_P5.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc_Etienne_P5.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    /* Get all task in any order */
    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    /* Get all task sort by name A to Z */
    @Query("SELECT * FROM Task ORDER BY name ASC")
    LiveData<List<Task>> getTasksNameASC();

    /* Get all task sort by name Z to A */
    @Query("SELECT * FROM Task ORDER BY name DESC")
    LiveData<List<Task>> getTasksNameDESC();

    /* Get all task sort by date recent to old */
    @Query("SELECT * FROM Task ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getTasksTimeASC();

    /* Get all task sort by date old to recent */
    @Query("SELECT * FROM Task ORDER BY creationTimestamp ASC")
    LiveData<List<Task>> getTasksTimeDESC();

    /* Get specific task by ID */
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    LiveData<List<Task>> getTasks(Long projectId);

    @Insert
    long insertTask(Task task);

    @Update
    int updateTask(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    int deleteTask(long taskId);
}