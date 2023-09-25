package com.bravo.invoice.ui.create_invoice.finalize_setup

import androidx.lifecycle.MutableLiveData
import com.bravo.basic.view.BaseViewModel
import com.bravo.invoice.common.Constants
import com.bravo.invoice.common.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FinalizeSetUpViewModel @Inject constructor(
    private val pref : Preferences
) : BaseViewModel() {
    private val notifyList by lazy {
        hashMapOf(
            Constants.BEFORE_DUE_DATE to true,
            Constants.ON_DUE_DATE to true,
            Constants.AFTER_DUE_DATE_3 to true,
            Constants.AFTER_DUE_DATE_7 to true
                )
    }
    var taxType : MutableLiveData<Int> = MutableLiveData(Constants.EXCLUSIVE)
        private set
    var taxRateValidate : MutableLiveData<Boolean?> = MutableLiveData()
        private set
    private var taxRate : Float = Constants.DEFAULT_TAX_RATE
    private var isChangedTax = false
    fun setNotifyDueDate(notifyKey : Int, isNotify : Boolean){
        notifyList[notifyKey] = isNotify
    }
    fun setDefaultNotify(){
        notifyList.replaceAll { _, _ ->
            true
        }
    }
    fun onTaxTypeChanged(taxType : Int){
        this.taxType.postValue(taxType)
    }
    fun onTaxRateChanged(value : CharSequence?){
        value?.let {
            taxRateValidate.value = it.isNotBlank()
            taxRate = it.toString().toFloatOrNull() ?: 0f
        }
    }
    fun saveTaxSetting(){
        isChangedTax = true
    }
    fun saveAllSetting(){
        pref.notifyBeforeDueDay.set(notifyList[Constants.BEFORE_DUE_DATE] ?: true)
        pref.notifyOnDueDay.set(notifyList[Constants.ON_DUE_DATE] ?: true)
        pref.notifyAfterDueDate3.set(notifyList[Constants.AFTER_DUE_DATE_3] ?: true)
        pref.notifyAfterDueDate7.set(notifyList[Constants.AFTER_DUE_DATE_7] ?: true)

       if(isChangedTax) {
           pref.taxType.set(taxType.value ?: Constants.EXCLUSIVE)
           pref.taxRate.set(taxRate)
       }
        pref.invoiceDesigned.set(pref.invoiceDesignedTemp.get())
    }
}