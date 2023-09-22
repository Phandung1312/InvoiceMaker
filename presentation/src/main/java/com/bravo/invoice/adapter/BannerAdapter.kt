package com.bravo.invoice.adapter

import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseAdapter
import com.bravo.invoice.databinding.ItemsBannerBinding
import com.bravo.invoice.models.Banner
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class BannerAdapter @Inject constructor() : BaseAdapter<Banner, ItemsBannerBinding>(ItemsBannerBinding::inflate) {
    val itemClicks : Subject<Int?> = PublishSubject.create()
    override fun bindItem(item: Banner, binding: ItemsBannerBinding, position: Int) {
        binding.ivBanner.setImageResource(item.bannerDrawableId)
        binding.root.clicks(withAnim = false) {
            if(position == 0) itemClicks.onNext(0)
            else itemClicks.onNext(item.bannerDrawableId)
        }
    }
}