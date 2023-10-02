package com.bravo.domain.repositories

import androidx.lifecycle.LiveData
import com.bravo.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun addItem(item : Item)
    fun getItem(id : Int) : Flow<Item>

    fun searchItems(query : String) : Flow<List<Item>>

    fun getAllItem() : Flow<List<Item>>

    fun removeItem(id : Int)

    fun updateItem(item : Item)
}