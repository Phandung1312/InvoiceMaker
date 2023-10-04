package com.bravo.invoice.ui.main.invoices.add_invoice

import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.PaymentInstructionClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentInstructionsFragment : BaseFragment<PaymentInstructionClass>(PaymentInstructionClass::inflate) {
    override fun initView() {
        binding.fragment = this
    }
    fun onSave(){

    }
    fun onBack(){
        popBackStack()
    }
}