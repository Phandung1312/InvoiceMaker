package com.bravo.invoice.ui.create_invoice.finalize_setup

import androidx.lifecycle.lifecycleScope
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.common.Preferences
import com.bravo.invoice.common.Utils
import com.bravo.invoice.databinding.DesignInvoiceStep5Class
import com.bravo.invoice.models.InvoiceDesign
import com.bravo.invoice.pdf.PdfManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DesignInvoiceStep5Fragment : BaseFragment<DesignInvoiceStep5Class>(DesignInvoiceStep5Class::inflate) {
    @Inject
    lateinit var preferences: Preferences
    override fun initView() {
        binding.fragment = this
        createInvoicePdf(preferences.invoiceDesigned.get())
    }
    private fun createInvoicePdf(invoiceDesign: InvoiceDesign) {
        lifecycleScope.launch(Dispatchers.Main) {
            val pdfManager = PdfManager(requireContext(), Utils.getSampleInvoice().copy(), invoiceDesign)
            val bitmap = pdfManager.getInvoicePDF()
            bitmap?.let{
                binding.ivTemplate.setImageBitmap(it)
            }
        }
    }
    fun onSendInvoice(){
    }

    fun onExplore(){
    }
}