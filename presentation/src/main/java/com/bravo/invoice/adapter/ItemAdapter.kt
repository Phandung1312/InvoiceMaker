package com.bravo.invoice.adapter

import androidx.core.view.isVisible
import com.bravo.basic.view.BaseAdapter
import com.bravo.domain.model.Item
import com.bravo.invoice.databinding.ItemsItemBinding
import javax.inject.Inject

class ItemAdapter @Inject constructor() : BaseAdapter<Item, ItemsItemBinding>(ItemsItemBinding::inflate) {
    override fun bindItem(item: Item, binding: ItemsItemBinding, position: Int) {
        binding.item = item
        binding.bottomLine.isVisible = position != itemCount - 1

    }
}