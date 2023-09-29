package com.bravo.invoice.ui.main.invoices.add_invoice

import androidx.activity.addCallback
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.AddInvoiceClass
import com.bravo.invoice.ui.items.ItemsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddInvoiceFragment : BaseFragment<AddInvoiceClass>(AddInvoiceClass::inflate) {
    override fun initView() {
        binding.fragment = this
    }

    override fun initListeners() {
        activity?.onBackPressedDispatcher?.addCallback {
            popBackStack(true)
        }
    }
    fun onClose(){
        popBackStack(true)
    }

    fun onNext(){
        addFragment(CompleteInvoiceFragment())
    }
    fun onAddItem(){
        addFragment(ItemsFragment())
    }

}