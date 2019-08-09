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

    private List<Project> mProjects;

    @Nullable
    private LiveData<List<Task>> mTasks;

    public TaskViewModel(TaskDataRepository taskDataRepository, ProjectDataRepository projectDataRepository, Executor executor) {
        mTaskDataRepository = taskDataRepository;
        mProjectDataRepository = projectDataRepository;
        mExecutor = executor;
    }

    public void initLists (){

        if (mProjects == null){
            new GetProjectListmod().execute();
        }

        if (this.mTasks == null){
            this.mTasks = mTaskDataRepository.getAllTask("NONE");
        }
    }

    public List<Project> getAllPro(){
        return this.mProjects;
    }

    public List<Project> getProject() {
        return mProjectDataRepository.getAllProjects();
    }

    public Project getSpecProject(Long projectId) {
        return mProjectDataRepository.getSpecProject(projectId);
    }


    public LiveData<List<Task>> getAllTasks (String sortBy){
        return this.mTaskDataRepository.getAllTask(sortBy);
    }

    public void createTask(Task task){
        mExecutor.execute(() ->{
            mTaskDataRepository.createItem(task);
        });
    }

    public void deleteTask(long taskId){
        mExecutor.execute(() -> {
            mTaskDataRepository.deleteItem(taskId);
        });
    }

    public void updateTask(Task task){
        mExecutor.execute(() -> {
            mTaskDataRepository.updateItem(task);
        });
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
