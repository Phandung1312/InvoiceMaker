package com.bravo.invoice.ui.project.adapter

import android.net.Uri
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseAdapter
import com.bravo.invoice.databinding.ItemAddFileBinding
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class AllFileAdapter @Inject constructor() :
    BaseAdapter<String, ItemAddFileBinding>(ItemAddFileBinding::inflate) {
    val itemClickFile: Subject<String> = PublishSubject.create()
    override fun bindItem(
        item: String, binding:
        ItemAddFileBinding, position: Int
    ) {
        try {
            val uri = Uri.parse(item)
            binding.imgFile.setImageURI(uri)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.root.clicks(withAnim = false) {
        }
    }

}