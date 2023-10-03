package com.bravo.invoice.ui.items.add_item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bravo.basic.view.BaseViewModel
import com.bravo.domain.model.Item
import com.bravo.domain.repositories.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : BaseViewModel() {
    var item : Item = Item()
    var itemLiveData  = MutableLiveData(Item())
    var itemNameValidate : MutableLiveData<Boolean?> = MutableLiveData(null)
        private set
    var unitType : MutableLiveData<Int> = MutableLiveData()
        private set
    fun setUnitType(type : Int){
        item.unitType = type
        unitType.postValue(type)
    }
    fun getItem(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            itemRepository.getItem(id).collect{
                item = it
                itemNameValidate.postValue(item.name.isNotEmpty())
                setUnitType(item.unitType)
                itemLiveData.postValue(item)
            }
        }
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
        if(itemLiveData.value?.name?.isNotEmpty() == true) {
            itemRepository.updateItem(item)
            return
        }
        itemRepository.addItem(item)
    }
    fun clearData(){
        item = Item()
        itemNameValidate.value  = null
        unitType.value = 1
    }
}