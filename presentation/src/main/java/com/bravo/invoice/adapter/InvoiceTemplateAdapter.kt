package com.bravo.invoice.adapter

import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseAdapter
import com.bravo.invoice.databinding.ItemsTemplateBinding
import com.bravo.invoice.models.InvoiceTemplate
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class InvoiceTemplateAdapter @Inject constructor(): BaseAdapter<InvoiceTemplate, ItemsTemplateBinding>(ItemsTemplateBinding::inflate) {
    val itemClicks : Subject<InvoiceTemplate> = PublishSubject.create()
    private var selectedIndex  = 0
        set(value){
            if(field == value) return
            notifyItemChanged(field)
            notifyItemChanged(value)
            field = value
        }
    override fun bindItem(item: InvoiceTemplate, binding: ItemsTemplateBinding, position: Int) {
        binding.apply {
            ivTemplate.setImageResource(item.imageDrawableId)
            tvTemplate.text = item.templateName
            cardView.strokeWidth = if(position == selectedIndex) 5 else 0
            root.clicks(withAnim = false) {
                selectedIndex = position
                itemClicks.onNext(item)
            }
        }

    }
    fun setIndex(index : Int){
        selectedIndex = index
    }
}