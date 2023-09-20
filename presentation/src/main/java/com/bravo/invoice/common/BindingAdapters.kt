package com.bravo.invoice.common

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bravo.basic.utils.AnimateUtils

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

    }
    @JvmStatic
    @BindingAdapter("visibleView")
    fun setVisibleView(view : View, isVisibility: Boolean?){
        isVisibility?.let {
            view.isVisible = it
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["animateVisible","option"])
    fun visibleViewWithAnimation(view : View, isVisibility: Boolean?, option : Int){
        isVisibility?.let {
            AnimateUtils.animateShowAndHide(view, it, option)
        }
    }
}