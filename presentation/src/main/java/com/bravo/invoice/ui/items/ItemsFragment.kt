package com.bravo.invoice.ui.items

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import com.bravo.basic.view.BaseFragment
import com.bravo.data.database.dao.ItemDao
import com.bravo.invoice.adapter.ItemAdapter
import com.bravo.invoice.common.extension.hideKeyboard
import com.bravo.invoice.databinding.ItemsClass
import com.bravo.invoice.ui.items.add_item.AddItemFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ItemsFragment : BaseFragment<ItemsClass>(ItemsClass::inflate) {
    private val viewModel by viewModels<ItemsViewModel>()
    @Inject lateinit var itemAdapter : ItemAdapter
    override fun initView() {
        binding.apply {
            fragment = this@ItemsFragment
            viewModel = this@ItemsFragment.viewModel
            rvItems.adapter = itemAdapter
        }
    }
    fun onAddItem(){
        addFragment(AddItemFragment())
    }
    fun onBack(){
        popBackStack()
    }
    override fun initListeners() {
       binding.edSearch.setOnEditorActionListener{_,actionId,_ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                binding.edSearch.hideKeyboard()
                true
            }
           else{
               false
            }
       }
    }

    override fun initData() {
        viewModel.items.observe(viewLifecycleOwner){
            it?.let {
                itemAdapter.data = it
            }
        }
        viewModel.getAllItem()
    }
}