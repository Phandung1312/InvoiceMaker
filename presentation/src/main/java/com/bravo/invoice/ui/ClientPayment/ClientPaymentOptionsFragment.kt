package com.bravo.invoice.ui.ClientPayment

import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.ClientPayment
import com.bravo.invoice.ui.project.BottomSheetAddFile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientPaymentOptionsFragment : BaseFragment<ClientPayment>(ClientPayment::inflate) {
//    private val bottomSheetPaypal by lazy {
//        BottomSheetPaypal()
//    }

    override fun initListeners() {
        binding.viewTerms.clicks(withAnim = false) {
            addFragment(TermsFragment())
        }
        binding.viewPaymentInstructions.clicks(withAnim = false)
        {

        }
        binding.viewPaypal.clicks(withAnim = false) {
          //  bottomSheetPaypal.show(childFragmentManager, bottomSheetPaypal.tag)
        }
    }

    override fun initData() {

    }
}