package com.bravo.invoice.adapter

import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseAdapter
import com.bravo.domain.model.Client
import com.bravo.invoice.databinding.ItemShowallClientsBinding
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class ClientAdapter  @Inject constructor() :
    BaseAdapter<Client, ItemShowallClientsBinding>(ItemShowallClientsBinding::inflate) {
    val itemClicks : Subject<Client> = PublishSubject.create()
    override fun bindItem(item: Client, binding: ItemShowallClientsBinding, position: Int) {
        binding.itemShowClients = item
        binding.root.clicks(withAnim = false) {
            itemClicks.onNext(item)
        }
    }

}