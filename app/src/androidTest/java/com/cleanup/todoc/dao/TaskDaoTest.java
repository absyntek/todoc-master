package com.cleanup.todoc.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase database;

    private static Long PROJECT_ID = 1L;
    private static Project PROJECT_DEMO = new Project(1L,"toto",1234);
    private static Task TASK_DEMO1 = new Task(1L,"aaaaa",443);
    private static Task TASK_DEMO2 = new Task(1L,"bbbbb",444);
    private static Task TASK_DEMO3 = new Task(1L,"zzzzz",442);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        this.database.mProjetDao().createProject(PROJECT_DEMO);
        Project project = this.database.mProjetDao().getSpecProject(PROJECT_ID);
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void getItemsWhenNoItemInserted() throws InterruptedException {
        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException {
        this.database.mProjetDao().createProject(PROJECT_DEMO);
        this.database.mTaskDao().insertTask(TASK_DEMO1);
        this.database.mTaskDao().insertTask(TASK_DEMO2);
        this.database.mTaskDao().insertTask(TASK_DEMO3);

        // TEST
        List<Task> items = LiveDataTestUtil.getValue(this.database.mTaskDao().getTasks());
        assertEquals(3, items.size());
    }

    @Test
    public void insertAndUpdateTasks() throws InterruptedException {
        this.database.mProjetDao().createProject(PROJECT_DEMO);
        this.database.mTaskDao().insertTask(TASK_DEMO1);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.mTaskDao().getTasks(PROJECT_ID)).get(0);
        this.database.mTaskDao().updateTask(taskAdded);

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getTasks(PROJECT_ID));
        assertTrue(tasks.size() == 1 && tasks.get(0).getName().equals("aaaaa"));
    }

    @Test
    public void insertAndDeleteItem() throws InterruptedException {
        this.database.mProjetDao().createProject(PROJECT_DEMO);
        this.database.mTaskDao().insertTask(TASK_DEMO1);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.mTaskDao().getTasks(PROJECT_ID)).get(0);
        this.database.mTaskDao().deleteTask(taskAdded.getId());

        //TEST
        List<Task> items = LiveDataTestUtil.getValue(this.database.mTaskDao().getTasks(PROJECT_ID));
        assertTrue(items.isEmpty());
    }
}
