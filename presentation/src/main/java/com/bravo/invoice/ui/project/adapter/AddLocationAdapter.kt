package com.bravo.invoice.ui.project.adapter

import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseAdapter
import com.bravo.invoice.databinding.ItemAddLocationBinding
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class AddLocationAdapter @Inject constructor() :
    BaseAdapter<String, ItemAddLocationBinding>(ItemAddLocationBinding::inflate) {
     val itemClickAddLocation : Subject<String> = PublishSubject.create()
    override fun bindItem(item: String, binding: ItemAddLocationBinding, position: Int) {
        binding.addressTextview.text = item
        binding.root.clicks(withAnim = false) {
            itemClickAddLocation.onNext(item)
        }
    }
}