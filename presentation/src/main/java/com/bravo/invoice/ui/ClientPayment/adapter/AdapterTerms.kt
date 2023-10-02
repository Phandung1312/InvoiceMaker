package com.bravo.invoice.ui.ClientPayment.adapter


import androidx.core.view.isGone
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseAdapter
import com.bravo.invoice.databinding.ItemTermsBinding
import com.bravo.invoice.models.Terms
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class AdapterTerms @Inject constructor() :
    BaseAdapter<Terms, ItemTermsBinding>(ItemTermsBinding::inflate) {
    val itemClickTerms: Subject<Terms> = PublishSubject.create()
    private var selectTerms = -1
    override fun bindItem(item: Terms, binding: ItemTermsBinding, position: Int) {
        binding.textItemTerms.text = item.itemTerms
        binding.root.clicks(withAnim = false) {
            itemClickTerms.onNext(item)
            selectTerms = if (selectTerms == position) {
                -1

            }
            else {
                position
            }
            notifyDataSetChanged()
        }
        binding.checkImg.isGone = selectTerms != position

    }


}