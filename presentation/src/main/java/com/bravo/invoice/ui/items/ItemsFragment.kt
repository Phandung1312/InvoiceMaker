package com.bravo.invoice.ui.items

import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.ItemsClass
import com.bravo.invoice.ui.items.add_item.AddItemFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsFragment : BaseFragment<ItemsClass>(ItemsClass::inflate) {

    override fun initView() {
        binding.fragment = this@ItemsFragment
    }
    fun onAddItem(){
        addFragment(AddItemFragment())
    }
}