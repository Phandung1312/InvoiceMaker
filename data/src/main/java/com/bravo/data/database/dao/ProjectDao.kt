package com.bravo.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.bravo.domain.model.ContactInfoProject
import com.bravo.domain.model.Project


@Dao
interface ProjectDao {
    @Insert(onConflict = REPLACE)
    fun insertProject(project: Project)

    @Query("SELECT * FROM Project WHERE status = 'Active'")
    fun getAllProjectActive(): LiveData<List<Project>>

    @Delete
    suspend fun deleteProject(project: Project)

    @Update
    suspend fun updateProject(project: Project)

    @Query("SELECT * FROM Project WHERE status = 'Complete'")
    fun getFilteredComplete(): LiveData<List<Project>>


    @Query("SELECT * FROM ContactInfoProject WHERE projectId = :id")
    fun getAllContactInfoByIDProject(id: Long): List<ContactInfoProject>

    @Query("SELECT * FROM ContactInfoProject")
    fun getAllContact(): LiveData<List<ContactInfoProject>>
    @Insert(onConflict = REPLACE)
    fun insertContact(contact: ContactInfoProject)


    @Delete
    suspend fun deleteContactInfoList(contact: ContactInfoProject)



}