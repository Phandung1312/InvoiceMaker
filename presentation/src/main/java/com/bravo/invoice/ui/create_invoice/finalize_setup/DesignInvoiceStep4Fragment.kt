package com.bravo.invoice.ui.create_invoice.finalize_setup


import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.DesignInvoiceStep4Class
import com.bravo.invoice.dialogs.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DesignInvoiceStep4Fragment : BaseFragment<DesignInvoiceStep4Class>(DesignInvoiceStep4Class::inflate) {
    private val viewModel by activityViewModels<FinalizeSetUpViewModel>()
    override fun initView() {
        binding.apply {
            fragment = this@DesignInvoiceStep4Fragment
            viewModel = this@DesignInvoiceStep4Fragment.viewModel
        }

    }
    fun onConfirm(){
        if(viewModel.taxRateValidate.value != false) {
            viewModel.saveTaxSetting()
            onSaveAllSetting()
        }
    }
    fun onUseExistSetting(){

        onSaveAllSetting()
    }
    private fun onSaveAllSetting(){
        val loadingDialog = LoadingDialog(requireContext())
        lifecycleScope.launch {
            loadingDialog.show()
            viewModel.saveAllSetting()
            delay(200)
            loadingDialog.dismiss()
            translateFragment(DesignInvoiceStep5Fragment())
        }
    }
}