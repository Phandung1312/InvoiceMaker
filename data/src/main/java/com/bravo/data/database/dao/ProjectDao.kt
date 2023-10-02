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

    @Query("SELECT * FROM Project WHERE id = :id")
    fun findById(id: Long): LiveData<Project?>

    @Delete
    suspend fun deleteProject(project: Project)


    @Query("DELETE FROM Project WHERE id = :objectId")
    fun deleteProjectById(objectId: Long)

    @Update
    suspend fun updateProject(project: Project)


    @Query("UPDATE Project SET notePrivate = :noteData WHERE id = :id")
    suspend fun updatePrivateNote(id: Long, noteData: String)

    @Query("UPDATE Project SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)

    @Query("UPDATE Project SET fileList = :fileList WHERE id = :id")
    suspend fun updateFileProject(id: Long, fileList: List<String>)


    @Query("SELECT * FROM Project WHERE status = 'Complete'")
    fun getFilteredComplete(): LiveData<List<Project>>


    @Query("SELECT * FROM ContactInfoProject WHERE projectId = :id")
    fun getAllContactInfoByIDProject(id: Long): List<ContactInfoProject>

    @Query("SELECT * FROM ContactInfoProject")
    fun getAllContact(): LiveData<List<ContactInfoProject>>

    @Insert(onConflict = REPLACE)
    fun insertContact(contact: ContactInfoProject)

    @Update
    suspend fun updateContactInfo(contact: ContactInfoProject)

    @Delete
    suspend fun deleteContactInfoList(contact: ContactInfoProject)


}