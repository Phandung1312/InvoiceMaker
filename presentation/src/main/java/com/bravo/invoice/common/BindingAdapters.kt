package com.bravo.invoice.common

import android.opengl.Visibility
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
    @BindingAdapter("dataEmailClient")
    fun setVisibilityEmail(textView: TextView,dataEmail :String?){
        dataEmail?.let {
            if(dataEmail.isNotEmpty()) {
                textView.isVisible = true
                textView.text = it
                return
            }
        }
        textView.isVisible = false
    }

    @JvmStatic
    @BindingAdapter("visibleView")
    fun setVisibleView(view : View, isVisibility: Boolean?){
        isVisibility?.let {
            view.isVisible = it
        }
    }

}