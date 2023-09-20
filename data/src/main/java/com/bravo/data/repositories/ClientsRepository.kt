package com.bravo.data.repositories

import androidx.lifecycle.LiveData
import com.bravo.data.database.dao.ClientDao
import com.bravo.domain.model.Client
import javax.inject.Inject

class ClientsRepository @Inject constructor(
    private val clientDao: ClientDao
) {
    fun insertClient(client: Client) {
        clientDao.insertClient(client)
    }
    suspend fun delete(client :Client) {
        clientDao.deleteClient(client)
    }

    suspend fun updateClient(client: Client)
    {
        clientDao.updateClient(client)
    }

    val getAllClient: LiveData<List<Client>> = clientDao.getAllClient()
}