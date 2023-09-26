package com.bravo.data.repositories

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.bravo.data.database.dao.ProjectDao
import com.bravo.domain.model.ContactInfoProject
import com.bravo.domain.model.Project
import javax.inject.Inject

class ProjectsRepository @Inject constructor(
    private val projectDao: ProjectDao
) {
    fun insertProject(project: Project) {
        projectDao.insertProject(project)
    }

    fun insertContact(contact: ContactInfoProject) {
        projectDao.insertContact(contact)
    }

    suspend fun deleteProject(project: Project) {
        projectDao.deleteProject(project)
    }

    suspend fun deleteContactInfo(contact: ContactInfoProject) {
        projectDao.deleteContactInfoList(contact)
    }

    suspend fun updateProject(project: Project) {
        projectDao.updateProject(project)
    }

    suspend fun updateContact(contact: ContactInfoProject) {
        projectDao.updateContactInfo(contact)
    }

    suspend fun updateNoteProject(id: Long, note: String) {
        projectDao.updatePrivateNote(id, note)
    }

    fun getAllContactByProjectID(parentId: Long): List<ContactInfoProject> {
        return projectDao.getAllContactInfoByIDProject(parentId)
    }


    fun getAll(): LiveData<List<ContactInfoProject>> {
        return projectDao.getAllContact()
    }

    val getAllProjectActive: LiveData<List<Project>> = projectDao.getAllProjectActive()
    val getAllProjectComplete: LiveData<List<Project>> = projectDao.getFilteredComplete()


}