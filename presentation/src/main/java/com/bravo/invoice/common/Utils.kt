package com.bravo.invoice.common

import android.graphics.Bitmap
import com.bravo.domain.model.InvoiceItem
import com.bravo.invoice.models.BusinessInfoInInvoiceUI
import com.bravo.invoice.models.ClientInInvoiceUI
import com.bravo.invoice.models.Invoice

object Utils {
    fun getSampleInvoice() : Invoice{
        return Invoice(
            businessInfo = BusinessInfoInInvoiceUI(
                tradingName = "Bravo",
                businessAddress = "Le Quang Dao",
                businessPhone = "093782332",
                businessEmail = "udhsih@gmail.com",
                businessWebsite = "ueuuwue.com.vn",
                additionalInfo = "Nothing"
            ),
            client = ClientInInvoiceUI(
                nameContact = "Nam Quang",
                emailContact = "quangdev@gmail.com",
                billingAddress = "An Thuong 21, Ngu Hanh Son, Da Nang.",
                phoneContact = "0932883283"
            ),
            date = "14/09/2023",
            dueDate = "14/09/2023",
            terms = "No",
            invoiceItems = listOf(
                InvoiceItem("Milk Tee", "Milk is made by Gordon James Ramsay", 25000f, 2, 5),
                InvoiceItem("Chicken", "Chicken from America", 25000f, 1, 10),
            )
        )
    }
    fun getScaleBitmap(originBitmap: Bitmap, maxDimension: Float): Bitmap {
        var ratio: Float = if (originBitmap.width > originBitmap.height) {
            maxDimension / originBitmap.width.toFloat()
        } else {
            maxDimension / originBitmap.height.toFloat()
        }
        val newWidth = (originBitmap.width * ratio).toInt()
        val newHeight = (originBitmap.height * ratio).toInt()
        return Bitmap.createScaledBitmap(originBitmap, newWidth, newHeight, true)
    }

}