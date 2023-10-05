package com.bravo.invoice.ui.main.invoices.add_invoice

import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.AddInvoiceClass
import com.bravo.invoice.ui.items.ItemsFragment
import com.bravo.invoice.ui.project.BottomSheetClients
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddInvoiceFragment : BaseFragment<AddInvoiceClass>(AddInvoiceClass::inflate) {
    private val viewModel by viewModels<AddInvoiceViewModel>()
    override fun initView() {
        binding.fragment = this
        binding.viewModel = viewModel
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
    fun onAddClient(){
        BottomSheetClients{ client ->
            viewModel.updateClient(client)
        }.show(parentFragmentManager, null)
    }
    fun onAddItem(){
        addFragment(ItemsFragment())
    }

    fun onAddPaymentInstruction(){
        addFragment(PaymentInstructionsFragment())
    }
}