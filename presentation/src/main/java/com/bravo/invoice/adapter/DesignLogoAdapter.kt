package com.bravo.invoice.adapter


import com.bravo.basic.extensions.clicks
import com.bravo.basic.extensions.setDrawableString
import com.bravo.basic.view.BaseAdapter
import com.bravo.invoice.databinding.ItemsDesignLogoBinding
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject



class DesignLogoAdapter @Inject constructor() : BaseAdapter<String, ItemsDesignLogoBinding>(ItemsDesignLogoBinding::inflate) {

    val itemClicks : Subject<String> = BehaviorSubject.createDefault("design_logo_1")
    var selectedItem  = 0
        private set
    override fun bindItem(item: String, binding: ItemsDesignLogoBinding, position: Int) {
        binding.ivLogo.setDrawableString(item)
        binding.root.clicks {
            selectedItem = position
            itemClicks.onNext(item)
        }
    }
}