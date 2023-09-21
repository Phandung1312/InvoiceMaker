package com.bravo.invoice.ui.create_invoice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bravo.basic.view.BaseViewModel
import com.bravo.invoice.common.Preferences
import com.bravo.invoice.models.InvoiceDesign
import com.uber.autodispose.android.autoDispose
import com.uber.autodispose.autoDispose
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateInvoiceViewModel @Inject constructor(
    private val preferences: Preferences
) : BaseViewModel() {
    var invoiceDesign : MutableLiveData<InvoiceDesign> = MutableLiveData(InvoiceDesign())
    private set
    init {
        invoiceDesign.value = preferences.invoiceDesigned.get()
    }

    fun setTemplate(templateId : Int){
        val temp = invoiceDesign.value!!
        temp.templateId = templateId
        viewModelScope.launch {
            preferences.invoiceDesigned.set(temp)
        }
    }

    fun setColor(color : Int){
        val temp = invoiceDesign.value!!
        temp.color = color
        viewModelScope.launch {
            preferences.invoiceDesigned.set(temp)
        }
    }

    fun setBanner(){

    }

    fun setWatermark(){

    }
    fun update(invoiceDesign: InvoiceDesign){
        this.invoiceDesign.value = invoiceDesign
    }

}