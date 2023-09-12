package com.bravo.invoice.ui.Client

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bravo.data.database.dao.ClientDao
import com.bravo.domain.model.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val clientDao: ClientDao,
) :ViewModel(){
    private lateinit var client: Client
    fun getLocalData():LiveData<List<Client>>{
        return clientDao.getAllClient()
    }
     fun insert(client: Client) {
        clientDao.insertClient(client)
    }
}
