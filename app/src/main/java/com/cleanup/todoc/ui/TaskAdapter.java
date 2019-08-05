package com.cleanup.todoc.ui;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.injectiions.Injection;
import com.cleanup.todoc.injectiions.ViewModelFactory;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public interface Listener {
        void onClickDeleteButton(long taskId);
    }
    private final Listener callback;
    private TaskViewModel mTaskViewModel;

    private List<Task> tasks;


    TaskAdapter(Listener callback, TaskViewModel taskViewModel) {
        this.tasks = new ArrayList<>();
        this.mTaskViewModel = taskViewModel;
        this.callback = callback;

    }

    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        taskViewHolder.bind(this.tasks.get(position), this.callback);
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }

    public void updateData(List<Task> tasks){
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final AppCompatImageView imgProject;

        private final TextView lblTaskName;
        private final TextView lblProjectName;
        private final AppCompatImageView imgDelete;

        private WeakReference<Listener> callbackWeakRef;

        private long taskId;

        public TaskViewHolder(View itemView) {
            super(itemView);
            imgProject = itemView.findViewById(R.id.img_project);
            lblTaskName = itemView.findViewById(R.id.lbl_task_name);
            lblProjectName = itemView.findViewById(R.id.lbl_project_name);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }

        void bind(Task task, TaskAdapter.Listener callback) {
            this.taskId = task.getId();
            this.callbackWeakRef = new WeakReference<>(callback);
            lblTaskName.setText(task.getName());
            imgDelete.setOnClickListener(this);

            new MyAssyncTask().execute(task.getProjectId());
        }

        private void setImgProjectAndProjectName(Project project){
            imgProject.setSupportImageTintList(ColorStateList.valueOf(project.getColor()));
            lblProjectName.setText(project.getName());
        }

        @Override
        public void onClick(View view) {
            TaskAdapter.Listener callback = callbackWeakRef.get();
            if (callback != null) callback.onClickDeleteButton(this.taskId);
        }

        private class MyAssyncTask extends AsyncTask <Long, Void, Project> {
            @Override
            protected Project doInBackground(Long... longs) {
                return mTaskViewModel.getSpecProject(longs[0]);
            }

            @Override
            protected void onPostExecute(Project project) {
                setImgProjectAndProjectName(project);
            }
        }
    }


}


