package com.cleanup.todoc_Etienne_P5.injectiions;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cleanup.todoc_Etienne_P5.repositories.ProjectDataRepository;
import com.cleanup.todoc_Etienne_P5.repositories.TaskDataRepository;
import com.cleanup.todoc_Etienne_P5.ui.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ProjectDataRepository mProjectDataRepository;
    private final TaskDataRepository mTaskDataRepository;
    private final Executor mExecutor;

    ViewModelFactory(ProjectDataRepository projectDataRepository, TaskDataRepository taskDataRepository, Executor executor){
        mProjectDataRepository = projectDataRepository;
        mTaskDataRepository = taskDataRepository;
        mExecutor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)){
            TaskViewModel taskViewModel = new TaskViewModel(mTaskDataRepository, mProjectDataRepository, mExecutor);
            if (modelClass.isInstance(taskViewModel)){
                return modelClass.cast(taskViewModel);
            }else throw new IllegalArgumentException("Unknown ViewModel class");
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
