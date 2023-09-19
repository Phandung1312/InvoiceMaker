package com.bravo.invoice.models

import com.bravo.invoice.R
import com.bravo.invoice.pdf.PdfManager

data class InvoiceDesign(
    var templateId : Int = PdfManager.IMPACT,
    var logo : LogoUI = LogoUI(),
    var additionalImageUI: AdditionalImageUI = AdditionalImageUI(),
    var color : Int = R.color.invoice_green
    )