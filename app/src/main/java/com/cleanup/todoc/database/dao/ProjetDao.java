package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject (Project project);

    @Query("SELECT * FROM Project")
    List<Project> getAllProject();

    @Query("SELECT * FROM Project WHERE id = :projectId")
    Project getSpecProject(Long projectId);
}