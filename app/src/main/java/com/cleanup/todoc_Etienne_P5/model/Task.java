package com.cleanup.todoc_Etienne_P5.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(foreignKeys = @ForeignKey(entity = Project.class,
        parentColumns = "id",
        childColumns = "projectId"),
        indices = {@Index("projectId")})
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long projectId;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private String name;

    private long creationTimestamp;


    public Task(Long projectId, @NonNull String name, long creationTimestamp) {

        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }


    // ------- GETTERS -------
    public long getId() {
        return id;
    }
    @Nullable public Long getProjectId() {
        return this.projectId;
    }
    @NonNull public String getName() {
        return name;
    }
    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    // ------- SETTERS -------
    public void setId(long id) {
        this.id = id;
    }
    private void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    private void setName(@NonNull String name) {
        this.name = name;
    }
    private void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}
