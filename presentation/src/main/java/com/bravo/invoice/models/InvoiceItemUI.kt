package com.bravo.invoice.models

import android.graphics.Bitmap
import android.net.Uri
import com.bravo.domain.model.InvoiceItem
import com.bravo.invoice.common.Constants


data class Invoice(
    val invoiceId : Int = 0,
    val businessInfo : BusinessInfoInInvoiceUI = BusinessInfoInInvoiceUI(),
    val client : ClientInInvoiceUI = ClientInInvoiceUI(),
    val date : String = "",
    val terms : String = "",
    val dueDate : String = "",
    val invoiceItems : List<InvoiceItem> = arrayListOf(),
    val paid : Float = 0f,
    val photos : List<Uri> = arrayListOf(),
    val comments : String? = null
)

data class ClientInInvoiceUI(
    val id : Int = 0,
    val nameContact : String = "",
    val emailContact : String = "",
    val billingAddress : String = "",
    val phoneContact : String = ""
)
data class BusinessInfoInInvoiceUI(
    val tradingName : String = "",
    val businessAddress : String = "",
    val businessPhone : String = "",
    val businessEmail : String = "",
    val businessWebsite : String ="",
    val additionalInfo : String = ""
)

data class LogoUI(
    var bitmap : Bitmap? = null,
    var size : Float = Constants.MEDIUM_SIZE,
    var alignment : Int = Constants.ALIGNMENT_CENTER
)

data class AdditionalImageUI(
    var bitmap: Bitmap? = null,
    var size : Float = Constants.MEDIUM_SIZE
)

