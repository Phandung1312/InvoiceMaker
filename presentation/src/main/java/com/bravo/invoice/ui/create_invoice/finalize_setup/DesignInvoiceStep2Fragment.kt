package com.bravo.invoice.ui.create_invoice.finalize_setup

import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.DesignInvoiceStep2Class

class DesignInvoiceStep2Fragment : BaseFragment<DesignInvoiceStep2Class>(DesignInvoiceStep2Class::inflate) {

    override fun initView() {
        binding.fragment = this
    }
    fun onNext(){
        translateFragment(DesignInvoiceStep3Fragment())
    }

}