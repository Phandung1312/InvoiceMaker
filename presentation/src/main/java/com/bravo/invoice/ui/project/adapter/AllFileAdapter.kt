package com.bravo.invoice.ui.project.adapter

import android.net.Uri
import androidx.core.view.isVisible
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
        item: String, binding: ItemAddFileBinding, position: Int
    ) {
        try {
            val uri = Uri.parse(item)
            if (item.contains(".pdf")) {
                binding.imgFile.setImageResource(com.bravo.invoice.R.drawable.ic_showpdf)
                binding.nameFile.text = item
            } else {
                binding.imgFile.setImageURI(uri)
                binding.nameFile.isVisible = false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.root.clicks(withAnim = false) {}
    }

}