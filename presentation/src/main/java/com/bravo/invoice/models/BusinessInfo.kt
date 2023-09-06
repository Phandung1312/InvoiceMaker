package com.bravo.invoice.models


data class BusinessInfo(
    var tradingName : String? = null,
    var businessAddress : String? = null,
    var phone : String? = null,
    var email : String? = null,
    var website : String? = null,
    var additionalInfo : String? = null,
    var legalBusinessName : String? = null,
    var regisBusinessAddress : String? = null,
    var businessPhone : String? = null,
    var employeeAmount : String? = null,
)