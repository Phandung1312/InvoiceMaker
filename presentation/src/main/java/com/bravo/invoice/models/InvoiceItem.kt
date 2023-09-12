package com.bravo.invoice.models

data class InvoiceItem(
    val name : String = "",
    val description : String? = null,
    val rate : Float = 0f,
    val quantity : Int = 0,
    val tax : Int? = null
)