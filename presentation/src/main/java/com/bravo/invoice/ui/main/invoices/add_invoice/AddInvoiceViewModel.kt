package com.bravo.invoice.ui.main.invoices.add_invoice

import androidx.lifecycle.MutableLiveData
import com.bravo.basic.view.BaseViewModel
import com.bravo.domain.model.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddInvoiceViewModel @Inject constructor() : BaseViewModel() {
    var client = MutableLiveData<Client>()
        private set

    fun updateClient(client : Client){
        this.client.value = client
    }
    fun removeClient(){
        this.client.value = null
    }
}