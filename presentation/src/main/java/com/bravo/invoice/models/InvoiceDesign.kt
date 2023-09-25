package com.bravo.invoice.models

import com.bravo.invoice.pdf.PdfManager

data class InvoiceDesign(
    var templateId : Int = PdfManager.IMPACT,
    var logo : LogoUI = LogoUI(),
    var banner : Int? = null,
    var watermark : Int? = null,
    var additionalImageUI: AdditionalImageUI = AdditionalImageUI(),
    var hiddenCompanyName : Boolean = false,
    var color : Int =  -16711926
    )