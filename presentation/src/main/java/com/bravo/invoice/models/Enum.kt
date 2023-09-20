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

enum class Color(
   val colorString : String
){
    A02E39("#A02E39"),
    C24654("#C24654"),
    E45764("#E45764"),
    FC6D7D("#FC6D7D"),
    FF8491("#FF8491"),

    B03622("#B03622"),
    D24129("#D24129"),
    EA5439("#EA5439"),
    F6644A("#F6644A"),
    FF806A("#FF806A"),

    FF1100("#FF1100"),
    FF3224("#FF3224"),
    FF5247("#FF5247"),
    FF6C63("#FF6C63"),
    FF908A("#FF908A"),

    FF0057("#FF0057"),
    FF1F6C("#FF1F6C"),
    FF4283("#FF4283"),
    FF6298("#FF6298"),
    FF89B1("#FF89B1"),

    DA00FF("#DA00FF"),
    DF25FF("#DF25FF"),
    E444FF("#E444FF"),
    E85EFF("#E85EFF"),
    ED7FFF("#ED7FFF"),
}