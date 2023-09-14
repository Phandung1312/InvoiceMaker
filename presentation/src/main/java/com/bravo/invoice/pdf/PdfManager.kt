package com.bravo.invoice.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
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

    private var currentHeight = 0f
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
        createReceiver()
        createInfo()
        createTableTitle()
        createTableContent()
        createInvoiceTotal()

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
            TYPEWRITER -> {
                currentX = PAGE_RIGHT_DISTANCE
                currentY = 190f
                paint.textAlign = Paint.Align.RIGHT
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
        paint.color = Color.BLACK
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
                currentY = 150f
            }

            TYPEWRITER -> {
                paint.textAlign = Paint.Align.CENTER
                currentX = (pageInfo1.pageWidth /2).toFloat()
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
        currentX = PAGE_RIGHT_DISTANCE
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
                paint.textAlign = Paint.Align.LEFT
                currentX = PAGE_LEFT_DISTANCE
                currentY = 168f
            }

            HIP -> {
                currentY = 137f
                paint.color = context.getColor(mColor)
            }

            CREATIVE -> {
                currentX = PAGE_LEFT_DISTANCE
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
        paint.color = Color.BLACK
        when(template){
            CLASSIC ->{
                startX = 0f
                endX = pageInfo1.pageWidth.toFloat()
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

            CREATIVE -> {
                startY = 171f
            }
        }
        canvas.drawRect(startX, startY, endX, startY + lineHeight, paint)
    }

    private fun createInfo() {
        paint.color = Color.BLACK
        paint.textSize = 8f
        paint.textAlign = Paint.Align.RIGHT
        paint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        var currentX = 0f
        var currentY = 0f
        when (template) {
            IMPACT, CLASSIC -> {
                currentX = (pageInfo1.pageWidth - 113).toFloat()
                currentY = 218f
            }

            MODERN -> {
                paint.textAlign = Paint.Align.LEFT
                currentX = PAGE_LEFT_DISTANCE
                currentY = 174f
            }

            MINIMAL -> {
                currentX = (pageInfo1.pageWidth - 113).toFloat()
                currentY = 232f
            }

            SHOWCASE -> {
                currentX = (pageInfo1.pageWidth - 113).toFloat()
                currentY = 232f
            }

            TYPEWRITER -> {
                paint.textAlign = Paint.Align.LEFT
                currentX = PAGE_LEFT_DISTANCE
                currentY = 190f
            }

            HIP -> {
                paint.textAlign = Paint.Align.LEFT
                currentX = PAGE_LEFT_DISTANCE
                currentY = 168f
            }

            CREATIVE -> {
                paint.textAlign = Paint.Align.LEFT
                currentX = PAGE_LEFT_DISTANCE
                currentY = 215f
            }
        }
        canvas.drawText("Invoice No:", currentX, currentY, paint)
        canvas.drawText("Date:", currentX, currentY + 15f, paint)
        canvas.drawText("Terms:", currentX, currentY + 30f, paint)
        canvas.drawText("Due Date:", currentX, currentY + 45f, paint)

        paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        currentX += when(template){
            IMPACT, CLASSIC, MINIMAL, SHOWCASE ->{
                84f
            }

            else -> {
                50f
            }
        }
        with(invoice) {
            canvas.drawText(invoiceId.toString(), currentX, currentY, paint)
            canvas.drawText(date, currentX, currentY + 15f, paint)
            canvas.drawText(terms, currentX, currentY + 30f, paint)
            canvas.drawText(dueDate, currentX, currentY + 45f, paint)
        }

        when (template) {
            TYPEWRITER -> {
                paint.color = context.getColor(R.color.invoice_vertical_line)
                canvas.drawLine((pageInfo1.pageWidth /2).toFloat(), 182f, (pageInfo1.pageWidth /2).toFloat(), currentHeight, paint)
            }

            HIP -> {
                paint.color = context.getColor(R.color.blue_black)
                currentHeight += 13f
                canvas.drawLine((pageInfo1.pageWidth /2).toFloat(), 150f, (pageInfo1.pageWidth /2).toFloat(), currentHeight, paint)
                canvas.drawLine(0f, 225f, (pageInfo1.pageWidth /2).toFloat(), 225f, paint)
                canvas.drawRect(0f, currentHeight, pageInfo1.pageWidth.toFloat(), currentHeight + 2f, paint)
                currentHeight += 2f
            }
        }
    }

    private fun createReceiver() {
        paint.color = Color.BLACK
        paint.textSize = 8f
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
                currentY = 218f
            }

            MODERN, MINIMAL, SHOWCASE -> {
                currentY = 232f
            }

            TYPEWRITER, HIP -> {
                currentY = 248f
            }

            CREATIVE -> {
                currentX = 198f
                currentY = 215f
            }
        }
        canvas.drawText("Bill to:", currentX, currentY, paint)

        paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        with(invoice.client) {
            canvas.drawText(nameContact, currentX + 50f, currentY, paint)
            canvas.drawText(emailContact, currentX + 50f, currentY + 15f, paint)
            val maxWidth = 100f
            val textPaint = TextPaint()
            textPaint.set(paint)
            val staticLayout = StaticLayout(
                billingAddress,
                textPaint,
                maxWidth.toInt(),
                Layout.Alignment.ALIGN_NORMAL,
                1f,
                0f,
                false
            )
            canvas.save()
            currentHeight = currentY + 22
            canvas.translate(currentX + 50f, currentHeight)
            staticLayout.draw(canvas)
            canvas.restore()
            currentHeight += staticLayout.height + 15f
            canvas.drawText(phoneContact, currentX + 50f, currentHeight , paint)
        }
    }

    private fun createTableTitle(){
        //Title Row
        currentHeight +=30f
        when(template){
            IMPACT, SHOWCASE, HIP, CREATIVE ->{
                if(template == IMPACT || template == SHOWCASE || template == CREATIVE){
                    paint.color = context.getColor(mColor)
                }
                if(template == HIP) paint.color = context.getColor(R.color.blue_black)
                if(template == CREATIVE) paint.alpha = 51 //(20%)
                canvas.drawRect(0f, currentHeight, pageInfo1.pageWidth.toFloat(), currentHeight + 25f, paint)
            }
            CLASSIC -> {
                paint.color = context.getColor(mColor)
                canvas.drawRect(0f, currentHeight - 5f,  pageInfo1.pageWidth.toFloat(), currentHeight, paint)
                paint.alpha = 51 //(20%)
                canvas.drawRect(0f, currentHeight,  pageInfo1.pageWidth.toFloat(), currentHeight + 25f, paint)
            }
            MODERN, MINIMAL -> {
                paint.color = if(template == MODERN) Color.BLACK else context.getColor(mColor)
                canvas.drawLine(0f, currentHeight, pageInfo1.pageWidth.toFloat(), currentHeight, paint)
                canvas.drawLine(0f, currentHeight, pageInfo1.pageWidth.toFloat(), currentHeight + 24f, paint)
            }
            TYPEWRITER ->{
                paint.color = context.getColor(mColor)
                canvas.drawLine(PAGE_LEFT_DISTANCE, currentHeight, pageInfo1.pageWidth.toFloat(), currentHeight + 2f, paint)
            }
        }

        paint.reset()
        paint.textAlign = Paint.Align.LEFT
        paint.textSize = 9f
        paint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        paint.color = when(template){
            SHOWCASE, HIP -> {
                context.getColor(R.color.white)
            }
            else -> Color.BLACK
        }
        currentHeight += 15f
        canvas.drawText("Description", 30f, currentHeight, paint)
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText("Quantity", (pageInfo1.pageWidth - 215).toFloat(), currentHeight, paint)
        canvas.drawText("Rate", (pageInfo1.pageWidth - 122).toFloat(), currentHeight, paint)
        canvas.drawText("Amount", (pageInfo1.pageWidth - 29).toFloat(), currentHeight, paint)
        currentHeight += 10
    }
    private fun createTableContent(){
        currentHeight -= 10f
        paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        invoice.invoiceItems.forEach { item ->
            paint.textAlign = Paint.Align.LEFT
            paint.textSize = 8f
            currentHeight += 30f
            canvas.drawText(item.name, 30f, currentHeight, paint)
            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText(
                item.quantity.toString(),
                (pageInfo1.pageWidth - 215).toFloat(),
                currentHeight,
                paint
            )
            canvas.drawText(
                "đ" + item.rate.toString(),
                (pageInfo1.pageWidth - 122).toFloat(),
                currentHeight,
                paint
            )
            canvas.drawText(
                "đ" + (item.quantity * item.rate).toString(),
                (pageInfo1.pageWidth - 29).toFloat(),
                currentHeight,
                paint
            )
            item.description?.let {
                currentHeight += 2f
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
                canvas.translate(30f, currentHeight)
                staticLayout.draw(canvas)
                currentHeight += staticLayout.height
                canvas.restore()
            }
        }
    }

    private fun createInvoiceTotal(){
        currentHeight += 30f

        paint.textAlign = Paint.Align.RIGHT
        paint.textSize = 8f

        canvas.drawText("Subtotal", (pageInfo1.pageWidth - 122).toFloat(), currentHeight, paint)
        val subTotal = invoice.invoiceItems.sumOf { (it.quantity * it.rate).toDouble() }
        canvas.drawText("đ$subTotal", (pageInfo1.pageWidth - 29).toFloat(), currentHeight, paint)

        currentHeight += 15f
        canvas.drawText("Discount", (pageInfo1.pageWidth - 122).toFloat(), currentHeight, paint)
        canvas.drawText("đ5000", (pageInfo1.pageWidth - 29).toFloat(), currentHeight, paint)

        var taxTenPercentTotal = 0f
        var taxFivePercentTotal = 0f
        if (invoice.invoiceItems.any { it.tax == 10 }) {
            currentHeight += 15f
            canvas.drawText("Tax 10%", (pageInfo1.pageWidth - 122).toFloat(), currentHeight, paint)
            invoice.invoiceItems.filter { it.tax == 10 }.forEach { item ->
                taxTenPercentTotal += (item.rate * item.quantity)
            }
            canvas.drawText(
                "đ$taxTenPercentTotal",
                (pageInfo1.pageWidth - 29).toFloat(),
                currentHeight,
                paint
            )
        }
        if(invoice.invoiceItems.any { it.tax == 5 }){
            currentHeight += 15f
            canvas.drawText("Tax 5%", (pageInfo1.pageWidth - 122).toFloat(), currentHeight, paint)
            invoice.invoiceItems.filter { it.tax == 5 }.forEach { item ->
                taxFivePercentTotal += (item.rate * item.quantity)
            }
            canvas.drawText(
                "đ$taxFivePercentTotal",
                (pageInfo1.pageWidth - 29).toFloat(),
                currentHeight,
                paint
            )
        }

        currentHeight += 15f

        canvas.drawText("Total", (pageInfo1.pageWidth - 122).toFloat(), currentHeight, paint)
        canvas.drawText("đ500000", (pageInfo1.pageWidth - 29).toFloat(), currentHeight, paint)

        currentHeight += 30f
        canvas.drawText("Paid", (pageInfo1.pageWidth - 122).toFloat(), currentHeight, paint)
        canvas.drawText("đ0", (pageInfo1.pageWidth - 29).toFloat(), currentHeight, paint)


        //Balance Due

        currentHeight += 30f
        when(template){
            IMPACT, MODERN, SHOWCASE, HIP ->{

                paint.color = if(template == SHOWCASE || template == HIP) context.getColor(mColor) else Color.BLACK
                canvas.drawRect(
                    (pageInfo1.pageWidth - 215).toFloat(),
                    currentHeight,
                    pageInfo1.pageWidth.toFloat(),
                    currentHeight + 26f,
                    paint
                )
            }
            CLASSIC, MINIMAL ->{
                paint.color = Color.BLACK
                canvas.drawLine((pageInfo1.pageWidth - 215).toFloat(),currentHeight, pageInfo1.pageWidth.toFloat(), currentHeight, paint)
                canvas.drawLine((pageInfo1.pageWidth - 215).toFloat(),currentHeight, pageInfo1.pageWidth.toFloat() + 26f, currentHeight, paint)
            }
            CREATIVE ->{
                paint.color = context.getColor(mColor)
                canvas.drawPath(getCornerRect(), paint)
            }
        }

        currentHeight += 19f



        paint.color = context.getColor(com.bravo.basic.R.color.textColorTertiary)
        paint.textSize = 13f
        paint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        canvas.drawText("Balance Due", (pageInfo1.pageWidth - 122).toFloat(), currentHeight, paint)
        canvas.drawText("đ15.850", (pageInfo1.pageWidth - 29).toFloat(), currentHeight, paint)

    }
    private fun getCornerRect() : Path{
        val path = Path()
        val rectWidth = 215f
        val rectHeight = 26f
        val cornerRadius = 10f

        val startX = (pageInfo1.pageWidth - 215).toFloat()
        val startY = currentHeight

        path.reset()
        path.moveTo(startX + cornerRadius, startY)
        path.lineTo(startX + rectWidth, startY)
        path.lineTo(startX + rectWidth, startY + rectHeight)
        path.lineTo(startX + cornerRadius, startY + rectHeight)
        path.arcTo(
            startX,
            startY + rectHeight - 2 * cornerRadius,
            startX + 2 * cornerRadius,
            startY + rectHeight,
            90f,
            90f,
            false
        )
        path.lineTo(startX, startY + cornerRadius)
        path.arcTo(
            startX,
            startY,
            startX + 2 * cornerRadius,
            startY + 2 * cornerRadius,
            180f,
            90f,
            false
        )
        path.close()
        path.close()
        return path
    }
}