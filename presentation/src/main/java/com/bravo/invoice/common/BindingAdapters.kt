package com.bravo.invoice.common

import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.inputmethod.EditorInfo
import android.widget.EditText
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
    @JvmStatic
    @BindingAdapter("animateOnClick")
    fun setAnimateOnClick(view: View, animateOnClick: Boolean) {

        if (animateOnClick) {
            view.setOnClickListener {

                val animation = ScaleAnimation(
                    0.0f, 1.0f,
                    0.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                )
                animation.duration = 500
                animation.repeatMode = Animation.REVERSE
                animation.repeatCount = 1
                view.startAnimation(animation)
            }
        } else {
            view.setOnClickListener(null)
        }
    }

    @JvmStatic
    @BindingAdapter("price")
    fun setFormatCurrency(view: TextView, price : Long?) {
        price?.let {
            val text = "đ" + Utils.formatCurrency(price.toDouble())
            view.text = text
        }
    }

    @JvmStatic
    @BindingAdapter("actionId")
    fun setFormatCurrency(editText: EditText, actionId : Int?) {
       actionId?.let {
           if(it == EditorInfo.IME_ACTION_DONE){
               editText.clearFocus()
           }
       }
    }
}