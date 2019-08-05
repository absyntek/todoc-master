package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjetDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjetDao mProjetDao;

    public ProjectDataRepository(ProjetDao projetDao) {
        mProjetDao = projetDao;
    }

    public List<Project> getAllProjects(){
        return this.mProjetDao.getAllProject();
    }

    public Project getSpecProject (Long projectId){
        return this.mProjetDao.getSpecProject(projectId);
    }
}
