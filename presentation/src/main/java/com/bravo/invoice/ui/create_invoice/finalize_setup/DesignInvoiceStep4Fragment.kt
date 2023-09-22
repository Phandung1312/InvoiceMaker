package com.bravo.invoice.ui.create_invoice.finalize_setup

import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.DesignInvoiceStep4Class

class DesignInvoiceStep4Fragment : BaseFragment<DesignInvoiceStep4Class>(DesignInvoiceStep4Class::inflate) {
    override fun initView() {
        binding.fragment = this
    }
    fun onConfirm(){
        translateFragment(DesignInvoiceStep5Fragment())
    }
}