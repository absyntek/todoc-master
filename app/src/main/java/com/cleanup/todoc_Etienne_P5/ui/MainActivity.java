package com.cleanup.todoc_Etienne_P5.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cleanup.todoc_Etienne_P5.R;
import com.cleanup.todoc_Etienne_P5.base.BaseActivity;
import com.cleanup.todoc_Etienne_P5.injectiions.Injection;
import com.cleanup.todoc_Etienne_P5.injectiions.ViewModelFactory;
import com.cleanup.todoc_Etienne_P5.model.Project;
import com.cleanup.todoc_Etienne_P5.model.Task;

import java.util.Date;
import java.util.List;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends BaseActivity implements TaskAdapter.Listener {

    private TaskViewModel mTaskViewModel;
    private List<Task> tasks;
    private TaskAdapter adapter;

    @Nullable
    public AlertDialog dialog = null;
    @Nullable
    private EditText dialogEditText = null;
    @Nullable
    private Spinner dialogSpinner = null;
    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView listTasks;
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView lblNoTasks;


    public MainActivity() { }

    @Override
    public int getLayoutContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listTasks = findViewById(R.id.list_tasks);
        lblNoTasks = findViewById(R.id.lbl_no_task);

        this.configureViewModel();

        this.mTaskViewModel.getAllTasks(SortMethod.NONE.toString()).observe(this, this::updateTaskList);

        this.configureRecyclerView();
        findViewById(R.id.fab_add_task).setOnClickListener(view -> showAddTaskDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        this.mTaskViewModel.getAllTasks("").removeObservers(this);
        switch (item.getItemId()){
            case R.id.filter_alphabetical :
                this.mTaskViewModel.getAllTasks(SortMethod.ALPHABETICAL.toString()).observe(this, this::updateTaskList);
                break;
            case R.id.filter_alphabetical_inverted :
                this.mTaskViewModel.getAllTasks(SortMethod.ALPHABETICAL_INVERTED.toString()).observe(this, this::updateTaskList);
                break;
            case R.id.filter_oldest_first :
                this.mTaskViewModel.getAllTasks(SortMethod.OLD_FIRST.toString()).observe(this, this::updateTaskList);
                break;
            case R.id.filter_recent_first :
                this.mTaskViewModel.getAllTasks(SortMethod.RECENT_FIRST.toString()).observe(this, this::updateTaskList);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickDeleteButton(long taskId) {
        mTaskViewModel.deleteTask(taskId);
    }

    private void configureRecyclerView() {
        this.adapter = new TaskAdapter(this, mTaskViewModel);
        this.listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.listTasks.setAdapter(this.adapter);
    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mTaskViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TaskViewModel.class);
        this.mTaskViewModel.initLists();
    }

    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {

                Task task = new Task(
                        taskProject.getId(),
                        taskName,
                        new Date().getTime()
                );

                mTaskViewModel.createTask(task);

                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else{
                dialogInterface.dismiss();
            }
        }
        // If dialog is aloready closed
        else {
            dialogInterface.dismiss();
        }
    }

    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();

        dialog.show();

        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);

        populateDialogSpinner(mTaskViewModel.getAllPro());
    }

    private void updateTasks() {
        if (this.tasks == null || this.tasks.size() == 0) {
            lblNoTasks.setVisibility(View.VISIBLE);
            listTasks.setVisibility(View.GONE);
        } else {
            lblNoTasks.setVisibility(View.GONE);
            listTasks.setVisibility(View.VISIBLE);
            adapter.updateData(tasks);
        }
    }

    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(dialogInterface -> {
            dialogEditText = null;
            dialogSpinner = null;
            dialog = null;
        });

        dialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        assert dialog != null;
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> onPositiveButtonClick(dialog));
        });

        return dialog;
    }

    private void populateDialogSpinner(List<Project> projects) {

        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, projects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }

    private enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }

    private void updateTaskList(List<Task> tasks){
        this.tasks = tasks;
        updateTasks();
    }
}