package com.bravo.invoice.ui.items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bravo.basic.view.BaseViewModel
import com.bravo.data.database.dao.ItemDao
import com.bravo.domain.model.Item
import com.bravo.domain.repositories.ItemRepository
import com.bravo.invoice.common.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val pref : Preferences
) : BaseViewModel() {
    val items = MutableLiveData<List<Item>>(arrayListOf())
    val autoSaveItem  = MutableLiveData<Boolean>()
    fun getAllItem(){
       viewModelScope.launch(Dispatchers.IO) {
           itemRepository.getAllItem().collect{
                items.postValue(it)
           }
       }
        autoSaveItem.postValue(pref.isAutoSaveItem.get())
    }

    fun onSearchTextChanged(text : CharSequence?){
       text?.let { queryText ->
            viewModelScope.launch(Dispatchers.IO) {
                itemRepository.searchItems(queryText.toString()).collect{ it ->
                    items.postValue(it)
               }
           }
       }
    }
    fun deleteItem(item : Item){
        itemRepository.deleteItem(item)
    }

    fun deleteItemsByIds(itemIds : List<Int>){
        itemRepository.deleteItemsByIds(itemIds)
    }

    fun onAutoSaveChanged(isAutoSave : Boolean){
         viewModelScope.launch {
             pref.isAutoSaveItem.set(isAutoSave)
             autoSaveItem.postValue(isAutoSave)
         }
    }
}