package com.bravo.invoice.ui.create_invoice.finalize_setup

import androidx.fragment.app.activityViewModels
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.DesignInvoiceStep2Class
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignInvoiceStep2Fragment : BaseFragment<DesignInvoiceStep2Class>(DesignInvoiceStep2Class::inflate) {
    private val viewModel by activityViewModels<FinalizeSetUpViewModel>()
    override fun initView() {
       binding.apply {
           fragment = this@DesignInvoiceStep2Fragment
           viewModel = this@DesignInvoiceStep2Fragment.viewModel
       }
    }
    fun onNext(){
        translateFragment(DesignInvoiceStep3Fragment())
    }
    fun onUseDefaultSetting(){
        viewModel.setDefaultNotify()
        translateFragment(DesignInvoiceStep3Fragment())
    }

}