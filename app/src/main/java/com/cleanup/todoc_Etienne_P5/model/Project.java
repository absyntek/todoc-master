package com.cleanup.todoc_Etienne_P5.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/**
 * <p>Models for project in which tasks are included.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity
public class Project {
    /**
     * The unique identifier of the project
     */
    @PrimaryKey
    private long id;

    /**
     * The name of the project
     */
    @NonNull private String name;

    /**
     * The hex (ARGB) code of the color associated to the project
     */
    @ColorInt private int color;

    /**
     * Instantiates a new Project.
     *
     * @param id    the unique identifier of the project to set
     * @param name  the name of the project to set
     * @param color the hex (ARGB) code of the color associated to the project to set
     */
    public Project(long id, @NonNull String name, @ColorInt int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    // ------- GETTERS -------
    public long getId() {
        return id;
    }
    @NonNull public String getName() {
        return name;
    }
    @ColorInt public int getColor() {
        return color;
    }

    // ------- SETTERS -------
    public void setId(long id) {
        this.id = id;
    }
    public void setName(@NonNull String name) {
        this.name = name;
    }
    public void setColor(int color) {
        this.color = color;
    }


    @Override
    @NonNull
    public String toString() {
        return getName();
    }
}
