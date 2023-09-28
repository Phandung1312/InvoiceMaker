package com.bravo.invoice.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
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
import com.bravo.invoice.common.BitmapSizeHelper
import com.bravo.invoice.common.Constants
import com.bravo.invoice.common.Utils
import com.bravo.invoice.models.Invoice
import com.bravo.invoice.models.InvoiceDesign
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PdfManager(
    private val context: Context,
    private val invoice: Invoice,
    private val invoiceDesign : InvoiceDesign,
) {
    private var document: PdfDocument = PdfDocument()
    private var pageInfo1: PageInfo = PageInfo.Builder(2100, 3700, 1).create()
    private var paint: Paint = Paint()
    private var myPage1: Page = document.startPage(pageInfo1)
    private var canvas: Canvas = myPage1.canvas
    private var bannerHeight = 0f
    private var logoHeight = 0f
    private var additionalImageHeight = 0f
    private var additionalHeight = 0f
    private var currentHeight = 0f
    companion object {
        const val IMPACT = 0
        const val CLASSIC = 1
        const val MODERN = 2
        const val MINIMAL = 3
        const val SHOWCASE = 4
        const val TYPEWRITER = 5
        const val HIP = 6
        const val CREATIVE = 7

        const val PAGE_LEFT_DISTANCE = 150f
        const val PAGE_RIGHT_DISTANCE = 1950f
    }

    fun getInvoicePDF(): Bitmap? {
        createBanner()
        createLogo()
        createAdditionalImage()
        createWatermark()
        additionalHeight = if(logoHeight > additionalImageHeight) logoHeight else additionalImageHeight
        additionalHeight += bannerHeight
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

            val scaleFactor = 1
            val bitmap = Bitmap.createBitmap(
                (page.width * scaleFactor),
                (page.height * scaleFactor),
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(bitmap)
            canvas.drawColor(0xFFFFFFFF.toInt())


            val matrix = Matrix()
            matrix.setScale(scaleFactor.toFloat(), scaleFactor.toFloat())


            canvas.drawBitmap(bitmap, matrix, null)

            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            pdfRenderer.close()
            return bitmap
        } catch (e: IOException) {
            return null
        }
    }

    private fun createBanner() {
        invoiceDesign.banner?.let {
            val bitmap = BitmapFactory.decodeResource(context.resources, it)

//            val scaleBitmap = Bitmap.createScaledBitmap(bitmap, pageInfo1.pageWidth, 70, true)
            val scaleBitmap = BitmapSizeHelper.createScaledBitmap(bitmap,pageInfo1.pageWidth, 350, BitmapSizeHelper.ScalingLogic.FIT )
            canvas.drawBitmap(scaleBitmap, 0f, 0f, paint)
            bannerHeight = 125f
        }
    }

    private fun createLogo() {
        invoiceDesign.logo.bitmap?.let { bitmap ->
            val maxDimension = invoiceDesign.logo.size
            logoHeight = maxDimension + 50f
//            val scaleBitmap = Utils.getScaleBitmap(bitmap, maxDimension)
            val scaleBitmap = BitmapSizeHelper.createScaledBitmap(bitmap,maxDimension.toInt(), maxDimension.toInt(), BitmapSizeHelper.ScalingLogic.FIT )
            paint.textAlign = Paint.Align.RIGHT
            val startX = when (invoiceDesign.logo.alignment) {
                Constants.ALIGNMENT_START -> PAGE_LEFT_DISTANCE
                Constants.ALIGNMENT_CENTER -> (pageInfo1.pageWidth / 2).toFloat() - (scaleBitmap.width / 2).toFloat()
                Constants.ALIGNMENT_END -> PAGE_RIGHT_DISTANCE - scaleBitmap.width.toFloat()
                else -> 0f
            }
            logoHeight = when(invoiceDesign.templateId){
                 TYPEWRITER, HIP -> 100f
                else -> 0f
            }
            canvas.drawBitmap(scaleBitmap, startX, 400f + bannerHeight, paint)
        }
    }
    private fun createWatermark(){
        invoiceDesign.watermark?.let {
            val bitmap = BitmapFactory.decodeResource(context.resources, it)
            canvas.drawBitmap(bitmap, -30f, (pageInfo1.pageHeight - bitmap.height).toFloat() + 10f, paint)
        }
    }
    private fun createAdditionalImage(){
        invoiceDesign.additionalImageUI.bitmap?.let { bitmap ->
            additionalImageHeight = invoiceDesign.additionalImageUI.size / 1.25f
            val scaleBitmap = Utils.getScaleBitmap(bitmap, additionalImageHeight)
            canvas.drawBitmap(scaleBitmap, PAGE_RIGHT_DISTANCE - scaleBitmap.width.toFloat(), 400f + bannerHeight, paint )
            additionalImageHeight = when(invoiceDesign.templateId){
                IMPACT, CLASSIC , CREATIVE-> additionalImageHeight - 100f
                else -> 0f
            }
        }
    }
    private fun createBusinessInfo() {
        var currentX = 0f
        var currentY = 0f
        paint.textSize = 35f
        when (invoiceDesign.templateId) {
            IMPACT, CLASSIC -> {
                currentX = (pageInfo1.pageWidth - PAGE_LEFT_DISTANCE)
                currentY = 550f + additionalHeight
                paint.textAlign = Paint.Align.RIGHT

            }

            MODERN -> {
                currentX = (pageInfo1.pageWidth - PAGE_LEFT_DISTANCE)
                currentY = 830f + additionalHeight
                paint.textAlign = Paint.Align.RIGHT
            }

            MINIMAL, SHOWCASE -> {
                currentX = PAGE_LEFT_DISTANCE
                currentY = 840f + additionalHeight
                paint.textAlign = Paint.Align.LEFT
            }
            TYPEWRITER -> {
                currentX = PAGE_RIGHT_DISTANCE
                currentY = 190f + additionalHeight
                paint.textAlign = Paint.Align.RIGHT
            }
            HIP -> {
                currentX = (pageInfo1.pageWidth - PAGE_LEFT_DISTANCE)
                currentY = 950f + additionalHeight
                paint.textAlign = Paint.Align.RIGHT
            }

            CREATIVE -> {
                currentX = (pageInfo1.pageWidth - PAGE_LEFT_DISTANCE)
                currentY = 605f + additionalHeight
                paint.textAlign = Paint.Align.RIGHT
            }
        }
        with(invoice.businessInfo) {
            canvas.drawText(businessAddress, currentX, currentY, paint)
            currentY += 50f
            canvas.drawText(businessPhone, currentX, currentY, paint)
            currentY += 50f
            canvas.drawText(businessEmail, currentX, currentY, paint)
            currentY += 50f
            canvas.drawText(businessWebsite, currentX, currentY, paint)
            currentY += 50f
            canvas.drawText(additionalInfo, currentX, currentY, paint)
        }
    }
    private fun createTitle() {
        var currentX = PAGE_LEFT_DISTANCE
        var currentY = 0f
        // Trading Name
        if(!invoiceDesign.hiddenCompanyName){
                paint.color = Color.BLACK
                paint.textSize = 105f
                paint.textAlign = Paint.Align.LEFT
                paint.typeface = Typeface.create(
                    ResourcesCompat.getFont(
                        context,
                        com.bravo.basic.R.font.inter_tight_semi_bold
                    ), Typeface.NORMAL
                )
                when (invoiceDesign.templateId) {
                    IMPACT, CLASSIC -> {
                        currentY = 890f + additionalHeight
                    }

                    MODERN, MINIMAL, SHOWCASE -> {
                        currentY = 750f + additionalHeight
                    }

                    TYPEWRITER -> {
                        paint.textAlign = Paint.Align.CENTER
                        paint.color = invoiceDesign.color
                        currentX = (pageInfo1.pageWidth / 2).toFloat()
                        currentY = 685f + additionalHeight
                    }

                    HIP -> {
                        currentY = 685f + additionalHeight
                    }

                    CREATIVE -> {
                        currentY = 795f + additionalHeight
                    }
                }
                canvas.drawText(invoice.businessInfo.tradingName, currentX, currentY, paint)
            }


        //Invoice text
        paint.color = Color.BLACK
        paint.textSize = 70f
        paint.textAlign = Paint.Align.RIGHT
        currentX = PAGE_RIGHT_DISTANCE
        when (invoiceDesign.templateId) {
            IMPACT, CLASSIC -> {
                currentY = 890f + additionalHeight
                paint.color = invoiceDesign.color
            }

            MODERN -> {
                currentY = 750f + additionalHeight
            }

            MINIMAL, SHOWCASE -> {
                currentY = 1020 + additionalHeight
            }

            TYPEWRITER -> {
                paint.textAlign = Paint.Align.LEFT
                currentX = PAGE_LEFT_DISTANCE
                currentY = 840f + additionalHeight
            }

            HIP -> {
                currentY = 685f + additionalHeight
                paint.color = invoiceDesign.color
            }

            CREATIVE -> {
                currentX = PAGE_LEFT_DISTANCE
                currentY = 970f + additionalHeight
                paint.color = invoiceDesign.color
                paint.textAlign = Paint.Align.LEFT
            }

        }
        canvas.drawText("Invoice", currentX, currentY, paint)

        //Title Line
        var startX = 0f
        var startY = 975f + additionalHeight
        var endX = pageInfo1.pageWidth.toFloat()
        var lineHeight = 5f
        paint.color = Color.BLACK
        when(invoiceDesign.templateId){
            CLASSIC ->{
                startX = 0f
                endX = pageInfo1.pageWidth.toFloat()
                lineHeight = 25f
                paint.color = invoiceDesign.color
            }
            MODERN ->{
                startY = 780f + additionalHeight
                paint.color = context.getColor(R.color.black_30_alpha)
            }
            MINIMAL, SHOWCASE ->{
                startY = 1070f + additionalHeight
                paint.color = context.getColor(R.color.black_30_alpha)
            }
            TYPEWRITER ->{
                startX = PAGE_LEFT_DISTANCE
                startY = 740f + additionalHeight
                paint.color = invoiceDesign.color
            }
            HIP ->{
                paint.color = context.getColor(R.color.blue_black)
                lineHeight = 10f
                startY = 740f + additionalHeight
            }

            CREATIVE -> {
                startY = 855f + additionalHeight
            }
        }
        canvas.drawRect(startX, startY, endX, startY + lineHeight, paint)
    }

    private fun createInfo() {
        paint.color = Color.BLACK
        paint.textSize = 40f
        paint.textAlign = Paint.Align.RIGHT
        paint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        var currentX = 0f
        var currentY = 0f
        when (invoiceDesign.templateId) {
            IMPACT, CLASSIC -> {
                currentX = (pageInfo1.pageWidth - 565).toFloat()
                currentY = 1090f + additionalHeight
            }

            MODERN -> {
                paint.textAlign = Paint.Align.LEFT
                currentX = PAGE_LEFT_DISTANCE
                currentY = 870f + additionalHeight
            }

            MINIMAL -> {
                currentX = (pageInfo1.pageWidth - 565).toFloat()
                currentY = 1160f + additionalHeight
            }

            SHOWCASE -> {
                currentX = (pageInfo1.pageWidth - 565).toFloat()
                currentY = 1160f + additionalHeight
            }

            TYPEWRITER -> {
                paint.textAlign = Paint.Align.LEFT
                currentX = PAGE_LEFT_DISTANCE
                currentY = 950f + additionalHeight
            }

            HIP -> {
                paint.textAlign = Paint.Align.LEFT
                currentX = PAGE_LEFT_DISTANCE
                currentY = 840f + additionalHeight
            }

            CREATIVE -> {
                paint.textAlign = Paint.Align.LEFT
                currentX = PAGE_LEFT_DISTANCE
                currentY = 1075f + additionalHeight
            }
        }
        canvas.drawText("Invoice No:", currentX, currentY, paint)
        canvas.drawText("Date:", currentX, currentY + 75f, paint)
        canvas.drawText("Terms:", currentX, currentY + 150f, paint)
        canvas.drawText("Due Date:", currentX, currentY + 225f, paint)

        paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        currentX += when(invoiceDesign.templateId){
            IMPACT, CLASSIC, MINIMAL, SHOWCASE ->{
                420f
            }

            else -> {
                250f
            }
        }
        with(invoice) {
            canvas.drawText(invoiceId.toString(), currentX, currentY, paint)
            canvas.drawText(date, currentX, currentY + 75f, paint)
            canvas.drawText(terms, currentX, currentY + 150f, paint)
            canvas.drawText(dueDate, currentX, currentY + 225f, paint)
        }

        when (invoiceDesign.templateId) {
            TYPEWRITER -> {
                paint.color = context.getColor(R.color.invoice_vertical_line)
                canvas.drawLine((pageInfo1.pageWidth /2).toFloat(), 910f + additionalHeight, (pageInfo1.pageWidth /2).toFloat(), currentHeight, paint)
            }

            HIP -> {
                paint.color = context.getColor(R.color.blue_black)
                currentHeight += 65f
                canvas.drawLine((pageInfo1.pageWidth /2).toFloat(), 750f + additionalHeight, (pageInfo1.pageWidth /2).toFloat(), currentHeight, paint)
                canvas.drawLine(0f, 1125f + additionalHeight, (pageInfo1.pageWidth /2).toFloat(), 1125f + additionalHeight, paint)
                canvas.drawRect(0f, currentHeight, pageInfo1.pageWidth.toFloat(), currentHeight + 10f, paint)
                currentHeight += 10f
            }
        }
    }

    private fun createReceiver() {
        paint.color = Color.BLACK
        paint.textSize = 40f
        paint.textAlign = Paint.Align.LEFT
        paint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        var currentX = PAGE_LEFT_DISTANCE
        var currentY = 0f
        when (invoiceDesign.templateId) {
            IMPACT, CLASSIC -> {
                currentY = 1090f + additionalHeight
            }

            MODERN, MINIMAL, SHOWCASE -> {
                currentY = 1160f + additionalHeight
            }

            TYPEWRITER, HIP -> {
                currentY = 1240f + additionalHeight
            }

            CREATIVE -> {
                currentX = 990f
                currentY = 1075f + additionalHeight
            }
        }
        canvas.drawText("Bill to:", currentX, currentY, paint)

        paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        with(invoice.client) {
            canvas.drawText(nameContact, currentX + 250f, currentY, paint)
            canvas.drawText(emailContact, currentX + 250f, currentY + 75f, paint)
            val maxWidth = 500f
            val textPaint = TextPaint()
            textPaint.set(paint)
            val staticLayout = StaticLayout(
                billingAddress,
                textPaint,
                maxWidth.toInt(),
                Layout.Alignment.ALIGN_NORMAL,
                5f,
                0f,
                false
            )
            canvas.save()
            currentHeight = currentY + 110
            canvas.translate(currentX + 250f, currentHeight)
            staticLayout.draw(canvas)
            canvas.restore()
            currentHeight += staticLayout.height + 75f
            canvas.drawText(phoneContact, currentX + 250f, currentHeight , paint)
        }
    }

    private fun createTableTitle(){
        //Title Row
        currentHeight +=150f
        when(invoiceDesign.templateId){
            IMPACT, SHOWCASE, HIP, CREATIVE ->{
                if(invoiceDesign.templateId == IMPACT || invoiceDesign.templateId == SHOWCASE || invoiceDesign.templateId == CREATIVE){
                    paint.color = invoiceDesign.color
                }
                if(invoiceDesign.templateId == HIP) paint.color = context.getColor(R.color.blue_black)
                if(invoiceDesign.templateId == CREATIVE) paint.alpha = 51 //(20%)
                canvas.drawRect(0f, currentHeight, pageInfo1.pageWidth.toFloat(), currentHeight + 125f, paint)
            }
            CLASSIC -> {
                paint.color = invoiceDesign.color
                canvas.drawRect(0f, currentHeight - 25f,  pageInfo1.pageWidth.toFloat(), currentHeight, paint)
                paint.alpha = 51 //(20%)
                canvas.drawRect(0f, currentHeight,  pageInfo1.pageWidth.toFloat(), currentHeight + 125f, paint)
            }
            MODERN, MINIMAL -> {
                paint.color = if(invoiceDesign.templateId == MODERN) Color.BLACK else invoiceDesign.color
                canvas.drawLine(0f, currentHeight, pageInfo1.pageWidth.toFloat(), currentHeight, paint)
                canvas.drawLine(0f, currentHeight + 120f, pageInfo1.pageWidth.toFloat(), currentHeight + 120f, paint)
            }
            TYPEWRITER ->{
                paint.color = invoiceDesign.color
                canvas.drawLine(PAGE_LEFT_DISTANCE, currentHeight + 125f, pageInfo1.pageWidth.toFloat(), currentHeight + 125f, paint)
            }
        }

        paint.reset()
        paint.textAlign = Paint.Align.LEFT
        paint.textSize = 45f
        paint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        paint.color = when(invoiceDesign.templateId){
            SHOWCASE, HIP -> {
                context.getColor(R.color.white)
            }
            else -> Color.BLACK
        }
        currentHeight += 75f
        canvas.drawText("Description", 150f, currentHeight, paint)
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText("Quantity", (pageInfo1.pageWidth - 1075).toFloat(), currentHeight, paint)
        canvas.drawText("Rate", (pageInfo1.pageWidth - 610).toFloat(), currentHeight, paint)
        canvas.drawText("Amount", (pageInfo1.pageWidth - 145).toFloat(), currentHeight, paint)
        currentHeight += 50
    }
    private fun createTableContent(){
        paint.reset()
        currentHeight -= 50f
        paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        invoice.invoiceItems.forEach { item ->
            paint.textAlign = Paint.Align.LEFT
            paint.textSize = 40f
            currentHeight += 150f
            canvas.drawText(item.name, 150f, currentHeight, paint)
            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText(
                item.quantity.toString(),
                (pageInfo1.pageWidth - 1075).toFloat(),
                currentHeight,
                paint
            )
            canvas.drawText(
                "đ" + item.rate.toString(),
                (pageInfo1.pageWidth - 610).toFloat(),
                currentHeight,
                paint
            )
            canvas.drawText(
                "đ" + (item.quantity * item.rate).toString(),
                (pageInfo1.pageWidth - 145).toFloat(),
                currentHeight,
                paint
            )
            item.description?.let {
                currentHeight += 10f
                paint.textSize = 30f
                paint.textAlign = Paint.Align.LEFT
                val maxWidth = 600f
                val textPaint = TextPaint()
                textPaint.set(paint)
                val staticLayout = StaticLayout(
                    item.description,
                    textPaint,
                    maxWidth.toInt(),
                    Layout.Alignment.ALIGN_NORMAL,
                    5f,
                    0f,
                    false
                )
                canvas.save()
                canvas.translate(150f, currentHeight)
                staticLayout.draw(canvas)
                currentHeight += staticLayout.height
                canvas.restore()
            }
        }
    }

    private fun createInvoiceTotal(){
        currentHeight += 150f

        paint.textAlign = Paint.Align.RIGHT
        paint.textSize = 40f

        canvas.drawText("Subtotal", (pageInfo1.pageWidth - 610).toFloat(), currentHeight, paint)
        val subTotal = invoice.invoiceItems.sumOf { (it.quantity * it.rate).toDouble() }
        canvas.drawText("đ$subTotal", (pageInfo1.pageWidth - 145).toFloat(), currentHeight, paint)

        currentHeight += 75f
        canvas.drawText("Discount", (pageInfo1.pageWidth - 610).toFloat(), currentHeight, paint)
        canvas.drawText("đ5000", (pageInfo1.pageWidth - 145f).toFloat(), currentHeight, paint)

        var taxTenPercentTotal = 0f
        var taxFivePercentTotal = 0f
        if (invoice.invoiceItems.any { it.tax == 10 }) {
            currentHeight += 75f
            canvas.drawText("Tax 10%", (pageInfo1.pageWidth - 610).toFloat(), currentHeight, paint)
            invoice.invoiceItems.filter { it.tax == 10 }.forEach { item ->
                taxTenPercentTotal += (item.rate * item.quantity)
            }
            canvas.drawText(
                "đ$taxTenPercentTotal",
                (pageInfo1.pageWidth - 145).toFloat(),
                currentHeight,
                paint
            )
        }
        if(invoice.invoiceItems.any { it.tax == 5 }){
            currentHeight += 75f
            canvas.drawText("Tax 5%", (pageInfo1.pageWidth - 610).toFloat(), currentHeight, paint)
            invoice.invoiceItems.filter { it.tax == 5 }.forEach { item ->
                taxFivePercentTotal += (item.rate * item.quantity)
            }
            canvas.drawText(
                "đ$taxFivePercentTotal",
                (pageInfo1.pageWidth - 145).toFloat(),
                currentHeight,
                paint
            )
        }

        currentHeight += 75f

        canvas.drawText("Total", (pageInfo1.pageWidth - 610).toFloat(), currentHeight, paint)
        canvas.drawText("đ500000", (pageInfo1.pageWidth - 145).toFloat(), currentHeight, paint)

        currentHeight += 150f
        canvas.drawText("Paid", (pageInfo1.pageWidth - 610).toFloat(), currentHeight, paint)
        canvas.drawText("đ0", (pageInfo1.pageWidth - 145).toFloat(), currentHeight, paint)


        //Balance Due

        currentHeight += 150f
        when(invoiceDesign.templateId){
            IMPACT, MODERN, SHOWCASE, HIP ->{

                paint.color = if(invoiceDesign.templateId == SHOWCASE || invoiceDesign.templateId == HIP  || invoiceDesign.templateId == MODERN) invoiceDesign.color else Color.BLACK
                if(invoiceDesign.templateId == HIP) paint.alpha = 51
                canvas.drawRect(
                    (pageInfo1.pageWidth - 1075).toFloat(),
                    currentHeight,
                    pageInfo1.pageWidth.toFloat(),
                    currentHeight + 130f,
                    paint
                )
            }
            CLASSIC, MINIMAL ->{
                paint.color = Color.BLACK
                canvas.drawLine((pageInfo1.pageWidth - 1075).toFloat(),currentHeight, pageInfo1.pageWidth.toFloat(), currentHeight, paint)
                canvas.drawLine((pageInfo1.pageWidth - 1075).toFloat(),currentHeight + 130f, pageInfo1.pageWidth.toFloat() + 130f, currentHeight + 130f, paint)
            }
            CREATIVE ->{
                paint.color = invoiceDesign.color
                paint.alpha = 51
                canvas.drawPath(getCornerRect(), paint)
            }
        }

        currentHeight += 85f


        paint.reset()
        paint.textSize = 65f
        paint.typeface = Typeface.create(
            ResourcesCompat.getFont(
                context,
                com.bravo.basic.R.font.inter_tight_semi_bold
            ), Typeface.NORMAL
        )
        when(invoiceDesign.templateId){
            IMPACT, MODERN, SHOWCASE ->{
                paint.color = Color.WHITE
            }
            CLASSIC ->{
                paint.color = invoiceDesign.color
            }
            else -> {
                paint.color = Color.BLACK
            }
        }
        canvas.drawText("Balance Due", (pageInfo1.pageWidth - 610).toFloat(), currentHeight, paint)
        canvas.drawText("đ15.850", (pageInfo1.pageWidth - 145).toFloat(), currentHeight, paint)

    }
    private fun getCornerRect() : Path{
        val path = Path()
        val rectWidth = 1075f
        val rectHeight = 130f
        val cornerRadius = 15f

        val startX = (pageInfo1.pageWidth - 1075).toFloat()
        val startY = currentHeight

        path.reset()
        path.moveTo(startX + cornerRadius, startY)
        path.lineTo(startX + rectWidth, startY)
        path.lineTo(startX + rectWidth, startY + rectHeight)
        path.lineTo(startX + cornerRadius, startY + rectHeight)
        path.arcTo(
            startX,
            startY + rectHeight - 10f * cornerRadius,
            startX + 10f * cornerRadius,
            startY + rectHeight,
            90f,
            90f,
            false
        )
        path.lineTo(startX, startY + cornerRadius)
        path.arcTo(
            startX,
            startY,
            startX + 10f * cornerRadius,
            startY + 10f * cornerRadius,
            180f,
            90f,
            false
        )
        path.close()
        path.close()
        return path
    }
}