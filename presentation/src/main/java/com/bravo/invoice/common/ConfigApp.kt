package com.bravo.invoice.common

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigApp @Inject constructor(
    prefs: Preferences
) {
    var url : String = ""
    var tabIndex = 0
    var passObjectClient = "OBJCLIENT"



}