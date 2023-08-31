package com.bravo.invoice.adapter

import com.bravo.basic.view.BaseAdapter
import com.bravo.invoice.databinding.ItemsIntroBinding
import com.bravo.invoice.models.Intro
import javax.inject.Inject

class IntroduceAdapter @Inject constructor() : BaseAdapter<Intro, ItemsIntroBinding>(ItemsIntroBinding::inflate) {
    override fun bindItem(item: Intro, binding: ItemsIntroBinding, position: Int) {

        binding.tvIntro.text = item.description
        val imageResourceId = binding.root.context.resources.getIdentifier(item.imageDrawableSrc,"drawable",binding.root.context.packageName)
        binding.ivIntro.setImageResource(imageResourceId)
    }

}