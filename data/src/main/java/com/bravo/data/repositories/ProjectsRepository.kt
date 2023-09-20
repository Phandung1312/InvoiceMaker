package com.bravo.data.repositories

import androidx.lifecycle.LiveData
import com.bravo.data.database.dao.ProjectDao
import com.bravo.domain.model.Project
import javax.inject.Inject

class ProjectsRepository @Inject constructor(
    private val projectDao: ProjectDao
) {
    fun insertProject(project: Project) {
        projectDao.insertProject(project)
    }

    suspend fun deleteProject(project: Project) {
        projectDao.deleteProject(project)
    }

    suspend fun updateProject(project: Project) {
        projectDao.updateProject(project)
    }

    val getAllProject: LiveData<List<Project>> = projectDao.getAllProject()
}