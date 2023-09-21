package com.bravo.invoice.adapter


import com.bravo.basic.extensions.clicks
import com.bravo.basic.extensions.setTint
import com.bravo.basic.view.BaseAdapter
import com.bravo.invoice.databinding.ItemsColorBinding
import com.bravo.invoice.models.Color
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class ColorAdapter @Inject constructor() : BaseAdapter<Color, ItemsColorBinding>(ItemsColorBinding::inflate) {
    val itemClicks : Subject<Int> = PublishSubject.create()
    private var selectedIndex = 0
        set(value){
            if(field == value) return
            notifyItemChanged(field)
            notifyItemChanged(value)
            field = value
        }
    override fun bindItem(item: Color, binding: ItemsColorBinding, position: Int) {
        val color = android.graphics.Color.parseColor(item.colorString)
        binding.ivColor.setTint(color)
        binding.cvColor.apply {
            strokeColor = if(selectedIndex == position) color else context.getColor(com.bravo.basic.R.color.backgroundPrimary)
        }
        binding.root.clicks(withAnim = false) {
            selectedIndex = position
            itemClicks.onNext(color)
        }
    }
    fun setSelectIndex(index : Int){
        selectedIndex = index
    }
}