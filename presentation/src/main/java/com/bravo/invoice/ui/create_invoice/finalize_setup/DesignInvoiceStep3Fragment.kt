package com.bravo.invoice.ui.create_invoice.finalize_setup

import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.DesignInvoiceStep3Class
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignInvoiceStep3Fragment : BaseFragment<DesignInvoiceStep3Class>(DesignInvoiceStep3Class::inflate)
{
    override fun initView() {
        binding.fragment = this
    }
    fun setTax(isChargeTax : Boolean){
        if(isChargeTax){
            translateFragment(DesignInvoiceStep4Fragment())
        }
        else{
            translateFragment(DesignInvoiceStep5Fragment())
        }
    }
}