package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao mTaskDao;

    public TaskDataRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    // GET ALL
    public LiveData<List<Task>> getAllTask() {
        return this.mTaskDao.getTasks();
    }

    // GET With Id
    public LiveData<List<Task>> getTask(long taskId) { return this.mTaskDao.getTasks(taskId); }

    // CREATE
    public void createItem(Task task){ this.mTaskDao.insertTask(task); }

    // DELETE
    public void deleteItem(long taskId){ this.mTaskDao.deleteTask(taskId); }

    // UPDATE
    public void updateItem(Task task){ this.mTaskDao.updateTask(task); }
}
