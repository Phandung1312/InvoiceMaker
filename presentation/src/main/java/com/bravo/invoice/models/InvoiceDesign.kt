package com.bravo.invoice.models

import com.bravo.invoice.R
import com.bravo.invoice.pdf.PdfManager

data class InvoiceDesign(
    var templateId : Int = PdfManager.IMPACT,
    var color : Int = R.color.invoice_green
    )