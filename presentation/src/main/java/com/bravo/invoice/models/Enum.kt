package com.bravo.invoice.models

import com.bravo.invoice.R
import com.bravo.invoice.pdf.PdfManager


enum class Intro(
     val imageDrawableSrc : String,
    val description : String
){
    First("first_introduce", "Save 10+ hours/week with simple invoice creation and customization"),
    Second("second_introduce", "Get paid 3 weeks faster by offering multiple\n" +
            "online payment methods"),
    Third("third_introduce", "Maintain order by monitoring project hours, appointments, and expenditures."),
    Fourth("fourth_introduce", "Secure additional projects using polished estimates and effective communication instruments.")
}

enum class InvoiceTemplate(
    val id : Int,
    val imageDrawableId: Int,
    val templateName : String
)
{
    Impact(PdfManager.IMPACT, R.drawable.impact_template, "Impact"),
    Classic(PdfManager.CLASSIC, R.drawable.classic_template, "Classic"),
    Modern(PdfManager.MODERN, R.drawable.modern_template, "Modern"),
    Minimal(PdfManager.MINIMAL, R.drawable.minimal_template, "Minimal"),
    Showcase(PdfManager.SHOWCASE, R.drawable.showcase_template, "Showcase"),
    Typewriter(PdfManager.TYPEWRITER, R.drawable.typewiter_template, "Typewriter"),
    Hip(PdfManager.HIP, R.drawable.hip_template, "Hip"),
    Creative(PdfManager.CREATIVE, R.drawable.creative_template, "Creative"),
}