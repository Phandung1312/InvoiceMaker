package com.bravo.invoice.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bravo.basic.view.BaseViewModel
import com.bravo.data.repositories.ProjectsRepository
import com.bravo.domain.model.ContactInfoProject
import com.bravo.domain.model.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository
) : BaseViewModel() {
    val getAllProjectActive: LiveData<List<Project>> = projectsRepository.getAllProjectActive
    val getAllProjectComplete: LiveData<List<Project>> = projectsRepository.getAllProjectComplete
    val contactList: MutableLiveData<List<ContactInfoProject>> = MutableLiveData()
    fun insertProjects(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectsRepository.insertProject(project)
        }
    }

    fun insertContact(contact: ContactInfoProject) {
        viewModelScope.launch(Dispatchers.IO) {
            projectsRepository.insertContact(contact)
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectsRepository.deleteProject(project)
        }
    }

    fun deleteContactInfo(contact: ContactInfoProject) {
        viewModelScope.launch(Dispatchers.IO) {
            projectsRepository.deleteContactInfo(contact)
        }
    }

    fun updateProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectsRepository.updateProject(project)
        }
    }


    fun updateContact(contact: ContactInfoProject) {
        viewModelScope.launch(Dispatchers.IO) {
            projectsRepository.updateContact(contact)
        }
    }

    fun updatePrivateNote(id: Long, noteData: String) {
        viewModelScope.launch(Dispatchers.IO) {
            projectsRepository.updateNoteProject(id, noteData)
        }
    }


    fun getContactById(idProject: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = projectsRepository.getAllContactByProjectID(idProject)
            contactList.postValue(result)
        }
    }


}