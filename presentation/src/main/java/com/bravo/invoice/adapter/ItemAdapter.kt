package com.bravo.invoice.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isVisible
import com.aitsuki.swipe.SwipeLayout
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseAdapter
import com.bravo.domain.model.Item
import com.bravo.invoice.databinding.ItemsItemBinding
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class ItemAdapter @Inject constructor() : BaseAdapter<Item, ItemsItemBinding>(ItemsItemBinding::inflate) {
    val itemClicks : Subject<Item> = PublishSubject.create()
    val itemSwipe : Subject<Item> = PublishSubject.create()
    private var isShowSelectView = false
    override fun bindItem(item: Item, binding: ItemsItemBinding, position: Int) {
        binding.item = item
        binding.apply {
            bottomLine.isVisible = position != itemCount - 1
            ivSelectItem.isVisible = isShowSelectView
            ivSelectItem.clicks(withAnim = false) {

            }
            swipeLayout.addListener(object : SwipeLayout.Listener{
                var isMenuShowing = false
                override fun onSwipe(menuView: View, swipeOffset: Float) {
                    if(swipeOffset > 0.5 && !isMenuShowing)  {
                        itemSwipe.onNext(item)
                        isMenuShowing = true
                    }
                    if(swipeOffset < 0.5) isMenuShowing = false
                }
            })
           root.clicks(withAnim = false) {
                itemClicks.onNext(item)
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun showMultiSelect(isShow : Boolean ){
        isShowSelectView = isShow
        notifyDataSetChanged()
    }
}