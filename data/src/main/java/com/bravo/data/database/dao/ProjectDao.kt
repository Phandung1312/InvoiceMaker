package com.bravo.data.database.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.bravo.domain.model.Project

@Dao
interface ProjectDao {
    @Insert(onConflict = REPLACE)
    fun insertProject(project: Project)

    @Query("SELECT * FROM Project")
    fun getAllProject(): LiveData<List<Project>>

    @Delete
    suspend fun deleteProject(project: Project)

    @Update
    suspend fun updateProject(project: Project)

    @Query("SELECT status FROM Project WHERE status LIKE '%' || :filter || '%' LIMIT 1")
    fun getFilteredComplete(filter: String): String

}