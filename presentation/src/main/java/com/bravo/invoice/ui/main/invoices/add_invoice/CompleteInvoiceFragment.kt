package com.bravo.invoice.ui.main.invoices.add_invoice

import androidx.lifecycle.lifecycleScope
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.common.Preferences
import com.bravo.invoice.common.Utils
import com.bravo.invoice.databinding.CompleteInvoiceClass
import com.bravo.invoice.models.InvoiceDesign
import com.bravo.invoice.pdf.PdfManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CompleteInvoiceFragment : BaseFragment<CompleteInvoiceClass>(CompleteInvoiceClass::inflate) {
    @Inject lateinit var pref : Preferences

    override fun initView() {
        createInvoicePdf(pref.invoiceDesigned.get())
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
}