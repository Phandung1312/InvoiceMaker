package com.bravo.invoice.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.core.content.res.ResourcesCompat
import com.bravo.invoice.R
import com.bravo.domain.model.InvoiceItem
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PdfManager(
    private val context: Context
) {
    fun getImpactPdf() : Bitmap? {
        val document = PdfDocument()
        val pageInfo1 = PdfDocument.PageInfo.Builder(420, 650, 1).create()
        val myPaint = Paint()
        val myPage1 = document.startPage(pageInfo1)
        val canvas = myPage1.canvas
        val address = "61 Le Quang Dao"
        val phoneNumber = "0938723732"
        val emailContact = "phandung@gmail.com"

        myPaint.textAlign = Paint.Align.RIGHT
        myPaint.textSize = 7f

        canvas.drawText(address, (pageInfo1.pageWidth - 30).toFloat(), 110F, myPaint)
        canvas.drawText(phoneNumber, (pageInfo1.pageWidth - 30).toFloat(), 120F, myPaint)
        canvas.drawText(emailContact, (pageInfo1.pageWidth - 30).toFloat(), 130F, myPaint)

        myPaint.textSize = 21f
        myPaint.textAlign = Paint.Align.LEFT
        myPaint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        canvas.drawText("Bravo", 30f, 178f, myPaint)

        myPaint.textSize = 14f
        myPaint.textAlign = Paint.Align.RIGHT
        myPaint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        myPaint.color = context.getColor(R.color.invoice_green)
        canvas.drawText("Invoice", (pageInfo1.pageWidth - 30).toFloat(), 178f, myPaint)

        val startY = 195f
        val endX = pageInfo1.pageWidth.toFloat()
        val lineHeight = 2F
        myPaint.color = Color.BLACK
        canvas.drawRect(0f, startY, endX, startY + lineHeight, myPaint)

        myPaint.textSize = 8f
        myPaint.textAlign = Paint.Align.LEFT
        myPaint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        canvas.drawText("Bill to:", 30f, 218f, myPaint)

        myPaint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        val clientName = "Quang"
        val clientEmail = "QuangDev@gmail.com"
        val billingAddress = "Novotel Building"
        val clientMobile = "08373237372"
        canvas.drawText(clientName, 86f, 218f, myPaint)
        canvas.drawText(clientEmail, 86f, 233f, myPaint)
        canvas.drawText(billingAddress, 86f, 248f, myPaint)
        canvas.drawText(clientMobile, 86f, 263f, myPaint)

        myPaint.textAlign = Paint.Align.RIGHT
        myPaint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        canvas.drawText("Invoice No:", (pageInfo1.pageWidth - 113).toFloat(), 218f, myPaint)
        canvas.drawText("Date:", (pageInfo1.pageWidth - 113).toFloat(), 233f, myPaint)
        canvas.drawText("Terms:", (pageInfo1.pageWidth - 113).toFloat(), 248f, myPaint)
        canvas.drawText("Due Date:", (pageInfo1.pageWidth - 113).toFloat(), 263f, myPaint)

        myPaint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        val invoiceNo = "1"
        val date = "06/09/2023"
        val terms = "NET 0"
        val dueDate = "06/09/2023"
        canvas.drawText(invoiceNo, (pageInfo1.pageWidth - 29).toFloat(), 218f, myPaint)
        canvas.drawText(date, (pageInfo1.pageWidth - 29).toFloat(), 233f, myPaint)
        canvas.drawText(terms, (pageInfo1.pageWidth - 29).toFloat(), 248f, myPaint)
        canvas.drawText(dueDate, (pageInfo1.pageWidth - 29).toFloat(), 263f, myPaint)


        myPaint.color = context.getColor(R.color.invoice_green)
        canvas.drawRect(0f, 297f, endX, 296f + 25, myPaint)

        myPaint.reset()
        myPaint.textAlign = Paint.Align.LEFT
        myPaint.textSize = 9f
        myPaint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        canvas.drawText("Description", 30f, 312f, myPaint)
        myPaint.textAlign = Paint.Align.RIGHT
        canvas.drawText("Quantity", (pageInfo1.pageWidth - 215).toFloat(), 312f, myPaint)
        canvas.drawText("Rate", (pageInfo1.pageWidth - 122).toFloat(), 312f, myPaint)
        canvas.drawText("Amount", (pageInfo1.pageWidth - 29).toFloat(), 312f, myPaint)

        val items = listOf(
            InvoiceItem("Milk Tee", "Milk is made by tea", 25000f, 2, 5),
            InvoiceItem("Chicken", "Muahaha", 25000f, 1, 10),
            InvoiceItem("Keyboard", "Keyboard of computer  ", 30000f, 3, null),
        )
        var currentY = 307f //(321 -14)
        myPaint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        items.forEach{ item ->
            myPaint.textAlign = Paint.Align.LEFT
            myPaint.textSize = 8f
            currentY +=30f
            canvas.drawText(item.name, 30f, currentY, myPaint)
            myPaint.textAlign = Paint.Align.RIGHT
            canvas.drawText(item.quantity.toString(), (pageInfo1.pageWidth - 215).toFloat(), currentY, myPaint)
            canvas.drawText("đ" + item.rate.toString(), (pageInfo1.pageWidth - 122).toFloat(), currentY, myPaint)
            canvas.drawText("đ" + (item.quantity * item.rate).toString(), (pageInfo1.pageWidth - 29).toFloat(), currentY, myPaint)
            item.description?.let {
                currentY +=2f
                myPaint.textSize = 6f
                myPaint.textAlign = Paint.Align.LEFT
                val maxWidth = 120f
                val textPaint = TextPaint()
                textPaint.set(myPaint)
                val staticLayout = StaticLayout(
                    item.description,
                    textPaint,
                    maxWidth.toInt(),
                    Layout.Alignment.ALIGN_NORMAL,
                    1f,
                    0f,
                    false
                )
                canvas.save()
                canvas.translate(30f, currentY)
                staticLayout.draw(canvas)
                canvas.restore()
            }
        }
        currentY += 11f

        myPaint.textAlign = Paint.Align.RIGHT
        myPaint.textSize = 8f

        canvas.drawText("Subtotal", (pageInfo1.pageWidth - 122).toFloat(), currentY, myPaint)
        val subTotal = items.sumOf { (it.quantity * it.rate).toDouble() }
        canvas.drawText("đ$subTotal", (pageInfo1.pageWidth - 29).toFloat(), currentY, myPaint)

        currentY += 15f
        canvas.drawText("Discount", (pageInfo1.pageWidth - 122).toFloat(), currentY, myPaint)
        canvas.drawText("đ5000", (pageInfo1.pageWidth - 29).toFloat(), currentY, myPaint)

        var taxTenPercentTotal = 0f
        var taxFivePercentTotal = 0f
        if(items.any { it.tax == 10 }){
            currentY +=15f
            canvas.drawText("Tax 10%", (pageInfo1.pageWidth - 122).toFloat(), currentY, myPaint)
            items.filter { it.tax == 10 }.forEach { item ->
                taxTenPercentTotal += (item.rate * item.quantity )
            }
            canvas.drawText("đ$taxTenPercentTotal", (pageInfo1.pageWidth - 29).toFloat(), currentY, myPaint)
        }
        if(items.any { it.tax == 5 }){
            currentY +=15f
            canvas.drawText("Tax 5%", (pageInfo1.pageWidth - 122).toFloat(), currentY, myPaint)
            items.filter { it.tax == 5 }.forEach { item ->
                taxFivePercentTotal += (item.rate * item.quantity )
            }
            canvas.drawText("đ$taxFivePercentTotal", (pageInfo1.pageWidth - 29).toFloat(), currentY, myPaint)
        }

        currentY += 15f

        canvas.drawText("Total", (pageInfo1.pageWidth - 122).toFloat(), currentY, myPaint)
        canvas.drawText("đ500000", (pageInfo1.pageWidth - 29).toFloat(), currentY, myPaint)

        currentY += 30f
        canvas.drawText("Paid", (pageInfo1.pageWidth - 122).toFloat(), currentY, myPaint)
        canvas.drawText("đ0", (pageInfo1.pageWidth - 29).toFloat(), currentY, myPaint)

        currentY += 30f
        myPaint.color = Color.BLACK
        canvas.drawRect((pageInfo1.pageWidth - 215).toFloat(), currentY, endX, currentY + 26f, myPaint)

        currentY += 19f

        myPaint.color = context.getColor(com.bravo.basic.R.color.textColorTertiary)
        myPaint.textSize = 13f
        myPaint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        canvas.drawText("Balance Due", (pageInfo1.pageWidth - 122).toFloat(), currentY, myPaint)
        canvas.drawText("đ15.850", (pageInfo1.pageWidth - 29).toFloat(), currentY, myPaint)
        document.finishPage(myPage1)
        val pdfFile = File(context.filesDir, "my_document.pdf")
        try {
            val fileOutputStream = FileOutputStream(pdfFile)
            document.writeTo(fileOutputStream)
            document.close()
            fileOutputStream.close()

            val pdfParcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(pdfParcelFileDescriptor)
            val page = pdfRenderer.openPage(0)
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawColor(0xFFFFFFFF.toInt())
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            pdfRenderer.close()
            return bitmap


        } catch (e: IOException) {
            return null
        }
    }
}