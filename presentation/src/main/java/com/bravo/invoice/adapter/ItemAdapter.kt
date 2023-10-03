package com.bravo.invoice.adapter

import android.annotation.SuppressLint
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.View
import androidx.core.util.forEach
import androidx.core.view.isVisible
import com.aitsuki.swipe.SwipeLayout
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseAdapter
import com.bravo.domain.model.Item
import com.bravo.invoice.R
import com.bravo.invoice.databinding.ItemsItemBinding
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class ItemAdapter @Inject constructor() : BaseAdapter<Item, ItemsItemBinding>(ItemsItemBinding::inflate) {
    val itemClicks : Subject<Item> = PublishSubject.create()
    val itemSwipe : Subject<Item> = PublishSubject.create()
    val selectedAmount : Subject<Int> = PublishSubject.create()
    private var isShowSelectView = false
    private lateinit var itemIds : ArrayList<Int>

    override fun bindItem(item: Item, binding: ItemsItemBinding, position: Int) {
        binding.item = item
        binding.apply {
            bottomLine.isVisible = position != itemCount - 1
            ivSelectItem.isVisible = isShowSelectView
            swipeLayout.clearListeners()
            swipeLayout.swipeFlags = 0
            if(!isShowSelectView){
                swipeLayout.swipeFlags = SwipeLayout.LEFT
                val listener = object : SwipeLayout.Listener{
                    override fun onMenuOpened(menuView: View) {
                        itemSwipe.onNext(item)
                    }
                }
                swipeLayout.addListener(listener)
                rootView.clicks(withAnim = false) {
                    itemClicks.onNext(item)
                }
            }
            else{
                rootView.clicks(withAnim = false) {
                    if(itemIds.contains(item.id)){
                        apply {
                            ivSelectItem.setImageResource(R.drawable.ic_uncheck)
                        }
                        itemIds.remove(item.id)
                    }
                    else{
                        apply {
                            ivSelectItem.setImageResource(R.drawable.ic_check)
                        }
                        itemIds.add(item.id)
                    }
                    selectedAmount.onNext(itemIds.size)
                }
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun showMultiSelect(isShow : Boolean ){
        isShowSelectView = isShow
        itemIds = arrayListOf()
        notifyDataSetChanged()
    }

    fun getItemsSelected(): List<Int> {
        return if (::itemIds.isInitialized) {
            itemIds
        } else {
            emptyList()
        }
    }
}