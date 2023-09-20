package com.bravo.invoice.common.pool

import android.graphics.Bitmap
import com.bravo.invoice.pdf.PdfManager
import com.bravo.invoice.ui.create_invoice.design_logo.DesignLogoFragment
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class InvoicePool @Inject constructor(

){
    var logo : Bitmap? = null
    var additionalImage : Bitmap? = null
    var currentOption : Int = DesignLogoFragment.LOGO
}