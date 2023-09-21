package com.bravo.data.repositories

import androidx.lifecycle.LiveData
import com.bravo.data.database.dao.ProjectDao
import com.bravo.domain.model.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    val getAllProjectActive: LiveData<List<Project>> = projectDao.getAllProjectActive()
    val getAllProjectComplete:LiveData<List<Project>> = projectDao.getFilteredComplete()
}