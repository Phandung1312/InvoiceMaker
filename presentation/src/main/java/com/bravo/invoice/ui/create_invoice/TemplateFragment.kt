package com.bravo.invoice.ui.create_invoice



import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.TemplateClass
import com.bravo.invoice.models.Invoice
import com.bravo.invoice.pdf.PdfManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TemplateFragment : BaseFragment<TemplateClass>(TemplateClass::inflate) {
    override fun initView() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createInvoicePdf()
    }
    private fun createInvoicePdf() {
        lifecycleScope.launch(Dispatchers.Main) {
            val pdfManager = PdfManager(requireContext(), Invoice(), PdfManager.IMPACT)
            val bitmap = pdfManager.getImpactPdf()
            bitmap?.let{
                binding.ivTemplate.setImageBitmap(it)
            }
        }
    }
}
