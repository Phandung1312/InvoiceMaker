package com.bravo.invoice.ui.setupinfo

import androidx.lifecycle.MutableLiveData
import com.bravo.basic.view.BaseViewModel
import com.bravo.invoice.common.Preferences
import com.bravo.invoice.models.BusinessInfo
import com.f2prateek.rx.preferences2.RxSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetUpInfoViewModel @Inject constructor(
    private val pref : Preferences
) : BaseViewModel() {
    private var businessInfo = BusinessInfo()

    var legalNameValidate = MutableLiveData<Boolean?>()
        private set
    var legalAddressValidate = MutableLiveData<Boolean?>()
        private set

    fun legalNameChanged(text: CharSequence?){
        text?.let {
            legalNameValidate.value = it.isNotBlank()
            businessInfo.legalBusinessName = it.toString()
        }
    }

    fun legalAddressChanged(text: CharSequence?){
        text?.let {
            legalAddressValidate.value = it.isNotBlank()
            businessInfo.regisBusinessAddress = it.toString()
        }
    }
    fun businessPhoneChanged(text: CharSequence?){
        text?.let {
            businessInfo.businessPhone = it.toString()
        }
    }

    fun tradingNameChanged(text: CharSequence?){
        text?.let {
            businessInfo.tradingName = it.toString()
        }
    }
    fun businessAddressChanged(text: CharSequence?){
        text?.let {
            businessInfo.businessAddress = it.toString()
        }
    }

    fun phoneChanged(text: CharSequence?){
        text?.let {
            businessInfo.phone = it.toString()
        }
    }

    fun urlChanged(text: CharSequence?){
        text?.let {
            businessInfo.website = it.toString()
        }
    }


    fun isValidateStep1() : Boolean{
        val fieldList = listOf(
            legalNameValidate,
            legalAddressValidate
        )
        val hasError = fieldList.any { it.value == null || it.value == false }
        for(field in fieldList){
            if(field.value == null) field.value = false
        }
        return !hasError
    }

    fun onNotify(isEnable : Boolean){
        pref.enableNotify.set(isEnable)
    }

    fun onMail(isEnable: Boolean){
        pref.enableMail.set(isEnable)
    }

    fun completeSetUp(){
        pref.businessInfo.set(businessInfo)
    }
}