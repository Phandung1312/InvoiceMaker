package com.bravo.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.bravo.domain.model.Client

@Dao
interface  ClientDao {
   @Insert(onConflict = REPLACE)
   fun insertClient(client: Client)

   @Query("SELECT * FROM Client")
   fun getAllClient():LiveData<List<Client>>
}