package com.bravo.invoice.ui.create_invoice


import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.TemplateClass
import java.io.File
import java.io.FileOutputStream


class TemplateFragment : BaseFragment<TemplateClass>(TemplateClass::inflate) {
    override fun initView() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createInvoicePdf()
    }
    private fun createInvoicePdf() {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(400, 600, 1).create()
        val myPaint = Paint()
        val myPage1 = document.startPage(pageInfo)
        val canvas = myPage1.canvas
        canvas.drawText("HHIHIHIHI", 40F, 50F, myPaint)
        document.finishPage(myPage1)

        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/FirstPDF.pdf")
        document.writeTo(FileOutputStream(file))
        document.close()
    }
}
