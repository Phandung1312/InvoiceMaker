package com.bravo.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bravo.domain.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Item)

    @Query("SELECT * FROM Item ORDER BY name ASC")
    fun getAll() : Flow<List<Item>>

    @Query("SELECT * FROM Item WHERE id = :id")
    fun getItem(id : Int) : Item

    @Query("SELECT * FROM Item WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchItem(query: String) : List<Item>

    @Delete
    fun delete(item : Item)

    @Query("DELETE FROM item WHERE id IN (:itemIds)")
    fun deleteItemsByIds(itemIds: List<Int>)

    @Update
    fun updateItem(item : Item)
}