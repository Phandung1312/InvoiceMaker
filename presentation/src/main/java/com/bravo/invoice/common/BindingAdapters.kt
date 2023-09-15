package com.bravo.invoice.common

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("isValidate")
    fun setVisibilityErrorText(textView: TextView,isValidate : Boolean?){
        textView.isVisible = isValidate == false

    }
    @JvmStatic
    @BindingAdapter("visibleView")
    fun setVisibleView(view : View, isVisibility: Boolean?){
        isVisibility?.let {
            view.isVisible = it
        }
    }

    @JvmStatic
    @BindingAdapter("animationVisible")
    fun visibleViewWithAnimation(view : View, isVisibility: Boolean?){
        isVisibility?.let {
            view.isVisible = it
        }
    }
}