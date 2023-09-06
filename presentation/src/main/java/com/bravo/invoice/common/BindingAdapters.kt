package com.bravo.invoice.common

import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("isValidate")
    fun setVisibilityErrorText(textView: TextView,isValidate : Boolean?){
        textView.isVisible = isValidate == false

    }
}