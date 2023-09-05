package com.bravo.invoice.ui.intro

import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseAdapter
import com.bravo.invoice.databinding.ItemsNearbyAddressBinding
import com.bravo.invoice.models.NearbyAddress
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


class NearbyAddressAdapter @Inject constructor() : BaseAdapter<NearbyAddress, ItemsNearbyAddressBinding>(ItemsNearbyAddressBinding::inflate) {
    val itemClicks : Subject<String> = PublishSubject.create()
    override fun bindItem(item: NearbyAddress, binding: ItemsNearbyAddressBinding, position: Int) {
        binding.nearbyAddress = item
        binding.root.clicks(withAnim = false) {
            itemClicks.onNext(item.address)
        }
    }
}