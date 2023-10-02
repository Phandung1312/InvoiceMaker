package com.bravo.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bravo.domain.model.Item
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Item)

    @Query("SELECT * FROM Item ORDER BY SUBSTR(name, 1, 1) ASC")
    fun getAll() : Flow<List<Item>>

    @Query("SELECT * FROM Item WHERE id = :id")
    fun getItem(id : Int) : Item

    @Query("SELECT * FROM Item WHERE name LIKE '%' || :query || '%' ORDER BY SUBSTR(name, 1, 1) ASC")
    fun searchItem(query: String) : List<Item>
}