package com.bravo.invoice.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.Page
import android.graphics.pdf.PdfDocument.PageInfo
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.core.content.res.ResourcesCompat
import com.bravo.domain.model.InvoiceItem
import com.bravo.invoice.R
import com.bravo.invoice.models.Invoice
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PdfManager(
    private val context: Context,
    private val invoice: Invoice,
    private val template: Int = IMPACT,
    private val mColor: Int = R.color.invoice_green,
) {
    private var document: PdfDocument = PdfDocument()
    private var pageInfo1: PageInfo = PageInfo.Builder(420, 650, 1).create()
    private var paint: Paint = Paint()
    private var myPage1: Page = document.startPage(pageInfo1)
    private var canvas: Canvas = myPage1.canvas

    companion object {
        const val IMPACT = 1
        const val CLASSIC = 2
        const val MODERN = 3
        const val MINIMAL = 4
        const val SHOWCASE = 5
        const val TYPEWRITER = 6
        const val HIP = 7
        const val CREATIVE = 8

        const val PAGE_LEFT_DISTANCE = 30f
        const val PAGE_RIGHT_DISTANCE = 390f
    }

    fun getImpactPdf(): Bitmap? {
        createBusinessInfo()
        createTitle()



        paint.textSize = 8f
        paint.textAlign = Paint.Align.LEFT
        paint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        canvas.drawText("Bill to:", 30f, 218f, paint)

        paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        val clientName = "Quang"
        val clientEmail = "QuangDev@gmail.com"
        val billingAddress = "Novotel Building"
        val clientMobile = "08373237372"
        canvas.drawText(clientName, 86f, 218f, paint)
        canvas.drawText(clientEmail, 86f, 233f, paint)
        canvas.drawText(billingAddress, 86f, 248f, paint)
        canvas.drawText(clientMobile, 86f, 263f, paint)

        paint.textAlign = Paint.Align.RIGHT
        paint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        canvas.drawText("Invoice No:", (pageInfo1.pageWidth - 113).toFloat(), 218f, paint)
        canvas.drawText("Date:", (pageInfo1.pageWidth - 113).toFloat(), 233f, paint)
        canvas.drawText("Terms:", (pageInfo1.pageWidth - 113).toFloat(), 248f, paint)
        canvas.drawText("Due Date:", (pageInfo1.pageWidth - 113).toFloat(), 263f, paint)

        paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        val invoiceNo = "1"
        val date = "06/09/2023"
        val terms = "NET 0"
        val dueDate = "06/09/2023"
        canvas.drawText(invoiceNo, (pageInfo1.pageWidth - 29).toFloat(), 218f, paint)
        canvas.drawText(date, (pageInfo1.pageWidth - 29).toFloat(), 233f, paint)
        canvas.drawText(terms, (pageInfo1.pageWidth - 29).toFloat(), 248f, paint)
        canvas.drawText(dueDate, (pageInfo1.pageWidth - 29).toFloat(), 263f, paint)

        var endX = pageInfo1.pageWidth.toFloat()
        paint.color = context.getColor(R.color.invoice_green)
        canvas.drawRect(0f, 297f, endX, 296f + 25, paint)

        paint.reset()
        paint.textAlign = Paint.Align.LEFT
        paint.textSize = 9f
        paint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        canvas.drawText("Description", 30f, 312f, paint)
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText("Quantity", (pageInfo1.pageWidth - 215).toFloat(), 312f, paint)
        canvas.drawText("Rate", (pageInfo1.pageWidth - 122).toFloat(), 312f, paint)
        canvas.drawText("Amount", (pageInfo1.pageWidth - 29).toFloat(), 312f, paint)

        val items = listOf(
            InvoiceItem("Milk Tee", "Milk is made by tea", 25000f, 2, 5),
            InvoiceItem("Chicken", "Muahaha", 25000f, 1, 10),
            InvoiceItem("Keyboard", "Keyboard of computer  ", 30000f, 3, null),
        )
        var currentY = 307f //(321 -14)
        paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        items.forEach { item ->
            paint.textAlign = Paint.Align.LEFT
            paint.textSize = 8f
            currentY += 30f
            canvas.drawText(item.name, 30f, currentY, paint)
            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText(
                item.quantity.toString(),
                (pageInfo1.pageWidth - 215).toFloat(),
                currentY,
                paint
            )
            canvas.drawText(
                "đ" + item.rate.toString(),
                (pageInfo1.pageWidth - 122).toFloat(),
                currentY,
                paint
            )
            canvas.drawText(
                "đ" + (item.quantity * item.rate).toString(),
                (pageInfo1.pageWidth - 29).toFloat(),
                currentY,
                paint
            )
            item.description?.let {
                currentY += 2f
                paint.textSize = 6f
                paint.textAlign = Paint.Align.LEFT
                val maxWidth = 120f
                val textPaint = TextPaint()
                textPaint.set(paint)
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

        paint.textAlign = Paint.Align.RIGHT
        paint.textSize = 8f

        canvas.drawText("Subtotal", (pageInfo1.pageWidth - 122).toFloat(), currentY, paint)
        val subTotal = items.sumOf { (it.quantity * it.rate).toDouble() }
        canvas.drawText("đ$subTotal", (pageInfo1.pageWidth - 29).toFloat(), currentY, paint)

        currentY += 15f
        canvas.drawText("Discount", (pageInfo1.pageWidth - 122).toFloat(), currentY, paint)
        canvas.drawText("đ5000", (pageInfo1.pageWidth - 29).toFloat(), currentY, paint)

        var taxTenPercentTotal = 0f
        var taxFivePercentTotal = 0f
        if (items.any { it.tax == 10 }) {
            currentY += 15f
            canvas.drawText("Tax 10%", (pageInfo1.pageWidth - 122).toFloat(), currentY, paint)
            items.filter { it.tax == 10 }.forEach { item ->
                taxTenPercentTotal += (item.rate * item.quantity)
            }
            canvas.drawText(
                "đ$taxTenPercentTotal",
                (pageInfo1.pageWidth - 29).toFloat(),
                currentY,
                paint
            )
        }
        if(items.any { it.tax == 5 }){
            currentY += 15f
            canvas.drawText("Tax 5%", (pageInfo1.pageWidth - 122).toFloat(), currentY, paint)
            items.filter { it.tax == 5 }.forEach { item ->
                taxFivePercentTotal += (item.rate * item.quantity)
            }
            canvas.drawText(
                "đ$taxFivePercentTotal",
                (pageInfo1.pageWidth - 29).toFloat(),
                currentY,
                paint
            )
        }

        currentY += 15f

        canvas.drawText("Total", (pageInfo1.pageWidth - 122).toFloat(), currentY, paint)
        canvas.drawText("đ500000", (pageInfo1.pageWidth - 29).toFloat(), currentY, paint)

        currentY += 30f
        canvas.drawText("Paid", (pageInfo1.pageWidth - 122).toFloat(), currentY, paint)
        canvas.drawText("đ0", (pageInfo1.pageWidth - 29).toFloat(), currentY, paint)

        currentY += 30f
        paint.color = Color.BLACK
        canvas.drawRect(
            (pageInfo1.pageWidth - 215).toFloat(),
            currentY,
            endX,
            currentY + 26f,
            paint
        )

        currentY += 19f

        paint.color = context.getColor(com.bravo.basic.R.color.textColorTertiary)
        paint.textSize = 13f
        paint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        canvas.drawText("Balance Due", (pageInfo1.pageWidth - 122).toFloat(), currentY, paint)
        canvas.drawText("đ15.850", (pageInfo1.pageWidth - 29).toFloat(), currentY, paint)
        document.finishPage(myPage1)
        val pdfFile = File(context.filesDir, "my_document.pdf")
        try {
            val fileOutputStream = FileOutputStream(pdfFile)
            document.writeTo(fileOutputStream)
            document.close()
            fileOutputStream.close()

            val pdfParcelFileDescriptor =
                ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
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

    private fun createBusinessInfo() {
        var currentX = 0f
        var currentY = 0f
        paint.textSize = 7f
        when (template) {
            IMPACT, CLASSIC -> {
                currentX = (pageInfo1.pageWidth - PAGE_LEFT_DISTANCE)
                currentY = 110f
                paint.textAlign = Paint.Align.RIGHT

            }

            MODERN -> {
                currentX = (pageInfo1.pageWidth - PAGE_LEFT_DISTANCE)
                currentY = 166f
                paint.textAlign = Paint.Align.RIGHT
            }

            MINIMAL, SHOWCASE -> {
                currentX = PAGE_LEFT_DISTANCE
                currentY = 166f
                paint.textAlign = Paint.Align.LEFT
            }

            HIP -> {
                currentX = (pageInfo1.pageWidth - PAGE_LEFT_DISTANCE)
                currentY = 166f
                paint.textAlign = Paint.Align.RIGHT
            }

            CREATIVE -> {
                currentX = (pageInfo1.pageWidth - PAGE_LEFT_DISTANCE)
                currentY = 121f
                paint.textAlign = Paint.Align.RIGHT
            }
        }
        with(invoice.businessInfo) {
            canvas.drawText(businessAddress, currentX, currentY, paint)
            currentY += 10
            canvas.drawText(businessPhone, currentX, currentY, paint)
            currentY += 10
            canvas.drawText(businessEmail, currentX, currentY, paint)
            currentY += 10
            canvas.drawText(businessWebsite, currentX, currentY, paint)
            currentY += 10
            canvas.drawText(additionalInfo, currentX, currentY, paint)
        }
    }

    private fun createTitle() {

        // Trading Name
        paint.textSize = 21f
        paint.textAlign = Paint.Align.LEFT
        paint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        var currentX = PAGE_LEFT_DISTANCE
        var currentY = 0f
        when (template) {
            IMPACT, CLASSIC -> {
                currentY = 178f
            }

            MODERN, MINIMAL, SHOWCASE -> {
                currentY = 165f
            }

            TYPEWRITER -> {
                paint.textAlign = Paint.Align.CENTER
                currentX = PAGE_RIGHT_DISTANCE
                currentY = 137f
            }

            HIP -> {
                currentY = 137f
            }

            CREATIVE -> {
                currentY = 159f
            }
        }
        canvas.drawText(invoice.businessInfo.tradingName, currentX, currentY, paint)

        //Invoice text

        paint.textSize = 14f
        paint.textAlign = Paint.Align.RIGHT
        currentX = (pageInfo1.pageWidth - PAGE_LEFT_DISTANCE)
        when (template) {
            IMPACT, CLASSIC -> {
                currentY = 178f
                paint.color = context.getColor(mColor)
            }

            MODERN -> {
                currentY = 165f
            }

            MINIMAL, SHOWCASE -> {
                currentY = 204f
            }

            TYPEWRITER -> {
                paint.color = context.getColor(mColor)
                paint.textAlign = Paint.Align.LEFT
                currentY = 168f
            }

            HIP -> {
                currentY = 137f
                paint.color = context.getColor(mColor)
            }

            CREATIVE -> {
                currentY = 194f
                paint.color = context.getColor(mColor)
                paint.textAlign = Paint.Align.LEFT
            }

        }
        canvas.drawText("Invoice", currentX, currentY, paint)

        //Title Line
        var startX = 0f
        var startY = 195f
        var endX = pageInfo1.pageWidth.toFloat()
        var lineHeight = 1f
        when(template){
            CLASSIC ->{
                startX = PAGE_LEFT_DISTANCE
                endX = PAGE_RIGHT_DISTANCE
                lineHeight = 5f
                paint.color = context.getColor(mColor)
            }
            MODERN ->{
                startY = 156f
                paint.color = context.getColor(R.color.black_30_alpha)
            }
            MINIMAL, SHOWCASE ->{
                startY = 214f
                paint.color = context.getColor(R.color.black_30_alpha)
            }
            TYPEWRITER ->{
                startX = PAGE_LEFT_DISTANCE
                startY = 148f
                paint.color = context.getColor(mColor)
            }
            HIP ->{
                paint.color = context.getColor(R.color.blue_black)
                lineHeight = 2f
                startY = 148f
            }
            CREATIVE ->{
                startY = 171f
            }
        }
        canvas.drawRect(startX, startY, endX, startY + lineHeight, paint)
    }

}