package com.cleanup.todoc_Etienne_P5.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.cleanup.todoc_Etienne_P5.model.Project;

import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {


    List<Project> mProjects;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutContentViewID());

    }

    public abstract int getLayoutContentViewID();

    protected void configureToolbar(){
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
