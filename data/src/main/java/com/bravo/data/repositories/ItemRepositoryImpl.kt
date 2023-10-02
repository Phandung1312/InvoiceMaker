package com.bravo.data.repositories

import com.bravo.data.database.dao.ItemDao
import com.bravo.domain.model.Item
import com.bravo.domain.repositories.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepositoryImpl @Inject constructor(
    private val itemDao : ItemDao
): ItemRepository {
    override fun addItem(item: Item) {
        itemDao.insert(item)
    }

    override fun getItem(id: Int) = flow{
        emit(itemDao.getItem(id))
    }

    override fun searchItems(query: String) = flow {
        emit(itemDao.searchItem(query))
    }

    override fun getAllItem(): Flow<List<Item>> {
        return itemDao.getAll()
    }

    override fun removeItem(id: Int) {
        TODO("Not yet implemented")
    }

    override fun updateItem(item: Item) {
        TODO("Not yet implemented")
    }
}