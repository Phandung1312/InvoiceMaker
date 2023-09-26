package com.bravo.invoice.ui.project.adapter

import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseAdapter
import com.bravo.domain.model.ContactInfoProject
import com.bravo.invoice.databinding.AddInfoContactItem
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class AllContactInfoAdapter @Inject constructor(): BaseAdapter<ContactInfoProject, AddInfoContactItem>(AddInfoContactItem::inflate) {
    val itemClicksInfoContact: Subject<ContactInfoProject> = PublishSubject.create()
    override fun bindItem(item: ContactInfoProject, binding: AddInfoContactItem, position: Int) {
        binding.itemShowContactInfoProject = item
        binding.root.clicks(withAnim = false) {
            itemClicksInfoContact.onNext(item)
        }
    }
}