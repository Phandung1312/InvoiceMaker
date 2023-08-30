package com.bravo.invoice.models


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