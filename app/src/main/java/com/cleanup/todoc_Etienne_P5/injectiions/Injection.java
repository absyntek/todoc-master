package com.cleanup.todoc_Etienne_P5.injectiions;

import android.content.Context;

import com.cleanup.todoc_Etienne_P5.database.TodocDatabase;
import com.cleanup.todoc_Etienne_P5.repositories.ProjectDataRepository;
import com.cleanup.todoc_Etienne_P5.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskDataRepository provideTaskDataSource(Context context){
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new TaskDataRepository(database.mTaskDao());
    }

    public static ProjectDataRepository provideProjectDataSource(Context context){
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new ProjectDataRepository(database.mProjetDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        ProjectDataRepository dataSourceProject = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceProject ,dataSourceTask, executor);
    }
}
