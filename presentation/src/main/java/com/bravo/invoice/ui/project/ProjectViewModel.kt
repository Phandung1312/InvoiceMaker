package com.bravo.invoice.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bravo.basic.view.BaseViewModel
import com.bravo.data.repositories.ProjectsRepository
import com.bravo.domain.model.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository
) : BaseViewModel() {
    val getAllProjects: LiveData<List<Project>> = projectsRepository.getAllProject
    fun insertProjects(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectsRepository.insertProject(project)
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectsRepository.deleteProject(project)
        }
    }

    fun updateProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectsRepository.updateProject(project)
        }
    }
}