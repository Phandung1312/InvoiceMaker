package com.bravo.invoice.ui.main.invoices.add_invoice

import androidx.activity.addCallback
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.AddInvoiceClass

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

}