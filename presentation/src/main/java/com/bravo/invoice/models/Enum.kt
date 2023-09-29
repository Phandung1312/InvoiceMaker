package com.bravo.invoice.models

import com.bravo.invoice.R
import com.bravo.invoice.common.Constants
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
enum class Banner(
    val bannerDrawableId : Int
)
{
    Banner0(R.drawable.empty_banner),
    Banner1(R.drawable.banner_1),
    Banner2(R.drawable.banner_2),
    Banner3(R.drawable.banner_3),
    Banner4(R.drawable.banner_4),
    Banner5(R.drawable.banner_5),
    Banner6(R.drawable.banner_6),
    Banner7(R.drawable.banner_7),
    Banner8(R.drawable.banner_8),
    Banner9(R.drawable.banner_9),
    Banner10(R.drawable.banner_10),
    Banner11(R.drawable.banner_11),
    Banner12(R.drawable.banner_12),
    Banner13(R.drawable.banner_13),
    Banner14(R.drawable.banner_14),
}
enum class Watermark(
    val iconId : Int,
    val watermarkId : Int
){
    Watermark0(R.drawable.ic_watermark_0, 0),
    Watermark1(R.drawable.ic_watermark_1, R.drawable.watermark_1),
    Watermark2(R.drawable.ic_watermark_2, R.drawable.watermark_2),
    Watermark3(R.drawable.ic_watermark_3, R.drawable.watermark_3),
    Watermark4(R.drawable.ic_watermark_4, R.drawable.watermark_4),
    Watermark5(R.drawable.ic_watermark_5, R.drawable.watermark_5),
    Watermark6(R.drawable.ic_watermark_6, R.drawable.watermark_6),
    Watermark7(R.drawable.ic_watermark_7, R.drawable.watermark_7),
    Watermark8(R.drawable.ic_watermark_8, R.drawable.watermark_8),
    Watermark9(R.drawable.ic_watermark_9, R.drawable.watermark_9),
    Watermark10(R.drawable.ic_watermark_10, R.drawable.watermark_10),
    Watermark11(R.drawable.ic_watermark_11, R.drawable.watermark_11),
    Watermark12(R.drawable.ic_watermark_12, R.drawable.watermark_12),
    Watermark13(R.drawable.ic_watermark_13, R.drawable.watermark_13),
    Watermark14(R.drawable.ic_watermark_14, R.drawable.watermark_14),
    Watermark15(R.drawable.ic_watermark_15, R.drawable.watermark_15),
    Watermark16(R.drawable.ic_watermark_16, R.drawable.watermark_16),
    Watermark17(R.drawable.ic_watermark_17, R.drawable.watermark_17),
    Watermark18(R.drawable.ic_watermark_18, R.drawable.watermark_18),
    Watermark19(R.drawable.ic_watermark_19, R.drawable.watermark_19),
    Watermark20(R.drawable.ic_watermark_20, R.drawable.watermark_20),
    Watermark21(R.drawable.ic_watermark_21, R.drawable.watermark_21),
    Watermark22(R.drawable.ic_watermark_22, R.drawable.ic_watermark_22),
    Watermark23(R.drawable.ic_watermark_23, R.drawable.watermark_23),
    Watermark24(R.drawable.ic_watermark_24, R.drawable.watermark_24),
    Watermark25(R.drawable.ic_watermark_25, R.drawable.watermark_25),
    Watermark26(R.drawable.ic_watermark_26, R.drawable.watermark_26),
    Watermark27(R.drawable.ic_watermark_27, R.drawable.watermark_27),
    Watermark28(R.drawable.ic_watermark_28, R.drawable.watermark_28),
    Watermark29(R.drawable.ic_watermark_29, R.drawable.watermark_29),
    Watermark30(R.drawable.ic_watermark_30, R.drawable.watermark_30),
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

    _5C00FF("#5C00FF"),
    _6C1AFF("#6C1AFF"),
    _792FFF("#792FFF"),
    _9052FF("#9052FF"),
    A877FF("#A877FF"),

    _008EFF("#008EFF"),
    _229DFF("#229DFF"),
    _3CA9FF("#3CA9FF"),
    _59B6FF("#59B6FF"),
    _7DC6FF("#7DC6FF"),

    _03B1FF("#03B1FF"),
    _1AB8FF("#1AB8FF"),
    _33C0FF("#33C0FF"),
    _52C9FF("#52C9FF"),
    _66CFFF("#66CFFF"),

    _00FFE7("#00FFE7"),
    _15FFE9("#15FFE9"),
    _34FFEC("#34FFEC"),
    _59FFEF("#59FFEF"),
    _7DFFF2("#7DFFF2"),

    _00FF0A("#00FF0A"),
    _20FF29("#20FF29"),
    _3FFF47("#3FFF47"),
    _5DFF63("#5DFF63"),
    _7AFF7F("#7AFF7F"),

    _89FF00("#89FF00"),
    _94FF19("#94FF19"),
    _A1FF35("#A1FF35"),
    _B2FF5A("#B2FF5A"),
    _C1FF7A("#C1FF7A"),

    E8FF00("#E8FF00"),
    EBFF23("#EBFF23"),
    EEFF45("#EEFF45"),
    F0FF5C("#F0FF5C"),
    F2FF74("#F2FF74"),

    FF9800("#FF9800"),
    FFA31C("#FFA31C"),
    FFB13F("#FFB13F"),
    FFBE60("#FFBE60"),
    FFCA7D("#FFCA7D"),
}

enum class UnitType(
    val typeId : Int,
    val typeName : String
){
    None(Constants.NONE, "None"),
    Days(Constants.DAYS, "Days"),
    Hours(Constants.HOURS, "Hours")
}
