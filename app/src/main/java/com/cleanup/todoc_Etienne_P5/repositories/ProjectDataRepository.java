package com.cleanup.todoc_Etienne_P5.repositories;

import com.cleanup.todoc_Etienne_P5.database.dao.ProjetDao;
import com.cleanup.todoc_Etienne_P5.model.Project;

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
