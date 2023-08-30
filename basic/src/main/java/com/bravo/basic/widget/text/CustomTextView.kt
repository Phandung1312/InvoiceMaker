package com.bravo.basic.widget.text

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.bravo.basic.R

class CustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) :  AppCompatTextView(context, attrs){
    init {
        val customFont = Typeface.createFromAsset(getContext().assets, "fonts/inter_tight_semi_bold.ttf")
        typeface = customFont

        val customTextColor = getContext().getColor(R.color.textColorPrimary)
        setTextColor(customTextColor)
    }
}