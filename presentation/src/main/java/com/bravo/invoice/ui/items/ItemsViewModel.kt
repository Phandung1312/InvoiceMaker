package com.bravo.invoice.ui.items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bravo.basic.view.BaseViewModel
import com.bravo.data.database.dao.ItemDao
import com.bravo.domain.model.Item
import com.bravo.domain.repositories.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
) : BaseViewModel() {
    val items = MutableLiveData<List<Item>>(arrayListOf())

    fun getAllItem(){
       viewModelScope.launch(Dispatchers.IO) {
           itemRepository.getAllItem().collect{
                items.postValue(it)
           }
       }
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
}