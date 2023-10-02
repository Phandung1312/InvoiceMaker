package com.bravo.invoice.ui.items.add_item

import androidx.lifecycle.MutableLiveData
import com.bravo.basic.view.BaseViewModel
import com.bravo.domain.model.Item
import com.bravo.domain.repositories.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : BaseViewModel() {
    var item : Item = Item()
    var itemNameValidate : MutableLiveData<Boolean?> = MutableLiveData(null)
        private set
    var unitType : MutableLiveData<Int> = MutableLiveData()
        private set
    fun setUnitType(type : Int){
        unitType.postValue(type)
    }

    fun onItemNameChanged(text : CharSequence?){
        text?.let {
            itemNameValidate.value = it.isNotBlank()
            item.name = it.toString()
        }
    }
    fun onItemDescriptionChanged(text : CharSequence?){
        text?.let {
            item.description = it.toString()
        }
    }
    fun onRateChanged(text : CharSequence?){
        text?.let {
            try{
                item.rate = it.toString().toLong()
            }
            catch (_: Exception){

            }
        }
    }
    fun onCostChanged(text : CharSequence?){
        text?.let {
            try{
                item.cost = it.toString().toLong()
            }
            catch (_: Exception){

            }
        }
    }
    fun onApplyTaxesChanged(isApply : Boolean){
        item.applyTaxes = isApply
    }

    fun isValidateInfo() : Boolean{
        itemNameValidate.value?.let {
            return it
        }
        itemNameValidate.value = true
        return false
    }
    fun saveItem(){
        itemRepository.addItem(item)
    }
}