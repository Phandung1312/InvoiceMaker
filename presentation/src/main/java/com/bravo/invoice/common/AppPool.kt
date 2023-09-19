package com.bravo.invoice.common

import android.graphics.Bitmap
import com.bravo.invoice.ui.create_invoice.design_logo.DesignLogoFragment
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppPool @Inject constructor(

){
    var logo : Bitmap? = null
    var additionalImage : Bitmap? = null
    var currentOption : Int = DesignLogoFragment.LOGO
}