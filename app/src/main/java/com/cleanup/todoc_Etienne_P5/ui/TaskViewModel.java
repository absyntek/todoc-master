package com.cleanup.todoc_Etienne_P5.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.cleanup.todoc_Etienne_P5.model.Project;
import com.cleanup.todoc_Etienne_P5.model.Task;
import com.cleanup.todoc_Etienne_P5.repositories.ProjectDataRepository;
import com.cleanup.todoc_Etienne_P5.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
    private final TaskDataRepository mTaskDataRepository;
    private final ProjectDataRepository mProjectDataRepository;
    private final Executor mExecutor;

    private String sortBy = "NONE";

    private List<Project> mProjects;

    @Nullable
    private LiveData<List<Task>> mTasks;

    public TaskViewModel(TaskDataRepository taskDataRepository, ProjectDataRepository projectDataRepository, Executor executor) {
        mTaskDataRepository = taskDataRepository;
        mProjectDataRepository = projectDataRepository;
        mExecutor = executor;
    }

    void initLists(){

        if (mProjects == null){
            new GetProjectListmod().execute();
        }

        if (this.mTasks == null){
            this.mTasks = mTaskDataRepository.getAllTask(sortBy);
        }
    }

    List<Project> getAllPro(){
        return this.mProjects;
    }

    public List<Project> getProject() {
        return mProjectDataRepository.getAllProjects();
    }

    Project getSpecProject(Long projectId) {
        return mProjectDataRepository.getSpecProject(projectId);
    }

    LiveData<List<Task>> getAllTasks(String sortBy){
        return this.mTaskDataRepository.getAllTask(sortBy);
    }

    void createTask(Task task){
        mExecutor.execute(() ->{
            mTaskDataRepository.createItem(task);
        });
    }

    void deleteTask(long taskId){
        mExecutor.execute(() -> {
            mTaskDataRepository.deleteItem(taskId);
        });
    }

    public void updateTask(Task task){
        mExecutor.execute(() -> {
            mTaskDataRepository.updateItem(task);
        });
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    private class GetProjectListmod extends AsyncTask<Void, Void, List<Project>> {
        @Override
        protected List<Project> doInBackground(Void... voids) {
            return mProjectDataRepository.getAllProjects();
        }

        @Override
        protected void onPostExecute(List<Project> projects) {
            mProjects = projects;
        }
    }
}
