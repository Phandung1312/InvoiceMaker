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

    private fun saveInvoiceDesign(
        templateId: Int? = null,
        color: Int? = null,
        banner: Int? = null,
        watermark: Int? = null
    ) {
        val temp = invoiceDesign.value!!
        templateId?.let { temp.templateId = it }
        color?.let { temp.color = it }
        banner?.let { temp.banner = it }
        watermark?.let { temp.watermark = it }

        viewModelScope.launch {
            preferences.invoiceDesigned.set(temp)
        }
    }

    fun setTemplate(templateId: Int) {
        saveInvoiceDesign(templateId = templateId)
    }

    fun setColor(color: Int) {
        saveInvoiceDesign(color = color)
    }

    fun setBanner(resourceId: Int?) {
        saveInvoiceDesign(banner = resourceId)
    }

    fun setWatermark(resourceId: Int?) {
        saveInvoiceDesign(watermark = resourceId)
    }
    fun update(invoiceDesign: InvoiceDesign){
        this.invoiceDesign.value = invoiceDesign
    }

}