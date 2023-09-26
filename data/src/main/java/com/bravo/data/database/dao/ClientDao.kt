package com.bravo.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.bravo.domain.model.Client
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Insert(onConflict = REPLACE)
    fun insertClient(client: Client)

    @Query("SELECT * FROM Client")
    fun getAllClient(): LiveData<List<Client>>

    @Delete
    suspend fun deleteClient(client: Client)

    @Update
    suspend fun updateClient(client: Client)


    @Query("SELECT * FROM Client WHERE billingName LIKE '%' || :titleText || '%' OR nameContact LIKE '%' || :titleText || '%'")
    fun searchClientList(titleText: String): LiveData<List<Client>>
}