package com.bravo.invoice.ui.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bravo.basic.view.BaseViewModel
import com.bravo.data.repositories.ClientsRepository
import com.bravo.domain.model.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val clientsRepository: ClientsRepository
) : BaseViewModel() {
    val getAllClient: LiveData<List<Client>> = clientsRepository.getAllClient
    fun insertClient(client: Client) {
        viewModelScope.launch(Dispatchers.IO) {
            clientsRepository.insertClient(client)
        }
    }

    fun deleteClient(client: Client) {
        viewModelScope.launch(Dispatchers.IO) {
            clientsRepository.delete(client)
        }
    }

    fun updateClient(client: Client) {
        viewModelScope.launch(Dispatchers.IO) {
            clientsRepository.updateClient(client)
        }
    }



}
