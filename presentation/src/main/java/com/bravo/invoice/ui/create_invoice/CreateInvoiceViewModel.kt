package com.bravo.invoice.ui.create_invoice

import androidx.lifecycle.MutableLiveData
import com.bravo.basic.view.BaseViewModel
import com.bravo.invoice.common.Preferences
import com.bravo.invoice.models.InvoiceDesign
import dagger.hilt.android.lifecycle.HiltViewModel
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
        invoiceDesign.value = temp
    }

    fun setLogo(){

    }

    fun setColor(){

    }

    fun setBanner(){

    }

    fun setWatermark(){

    }
}