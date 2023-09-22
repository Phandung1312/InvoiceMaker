package com.bravo.invoice.adapter

import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseAdapter
import com.bravo.invoice.databinding.ItemsWatermarkBinding
import com.bravo.invoice.models.Watermark
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class WatermarkAdapter @Inject constructor() : BaseAdapter<Watermark, ItemsWatermarkBinding>(ItemsWatermarkBinding::inflate) {
    val itemClicks : Subject<Int> = PublishSubject.create()
    private var selectedIndex = 0
        set(value){
            if(field == value) return
            notifyItemChanged(field)
            notifyItemChanged(value)
            field = value
        }
    override fun bindItem(item: Watermark, binding: ItemsWatermarkBinding, position: Int) {
       binding.apply{
           ivWatermark.setImageResource(item.iconId)
           cvWatermark.strokeWidth = if(position == selectedIndex) 2 else 0
           root.clicks(withAnim = false) {
               selectedIndex = position
               itemClicks.onNext(item.watermarkId)
           }
       }
    }
    fun setIndex(index : Int){
        selectedIndex = index
    }
}