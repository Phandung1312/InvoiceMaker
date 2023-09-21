package com.bravo.basic.extensions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Build
import android.os.SystemClock
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bravo.basic.widget.scale.PushDownAnim
import com.google.android.material.animation.ArgbEvaluatorCompat
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment

fun View.clicks(debounce: Long = 250, withAnim: Boolean = true, clicks: () -> Unit) {
    if (withAnim){
        var lastClickTime: Long = 0
        PushDownAnim.setPushDownAnimTo(this)
            .setOnClickListener {
                if (SystemClock.elapsedRealtime() - lastClickTime < debounce) return@setOnClickListener
                else clicks()
                lastClickTime = SystemClock.elapsedRealtime()
            }
    } else {
        setOnClickListener { clicks() }
    }
}

fun View.setBackgroundTint(color: Int) {
    // API 21 doesn't support this
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
        background?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    backgroundTintList = ColorStateList.valueOf(color)
}

fun View.setPaddingHorizontal(padding: Int) {
    setPadding(padding, paddingTop, padding, paddingBottom)
}

fun View.setPaddingVertical(padding: Int) {
    setPadding(paddingLeft, padding, paddingRight, padding)
}

fun TextView.setTextSizeDimens(dimenRes: Int) {
    setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(dimenRes))
}

fun View.addShadow(colorRes: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        outlineAmbientShadowColor = context.getColorCompat(colorRes)
        outlineSpotShadowColor = context.getColorCompat(colorRes)
    }
}

fun View.animateBackground(endColor: Int) {
    clearAnimation()
    val valueAnimator = ValueAnimator.ofObject(ArgbEvaluatorCompat(), backgroundTintList?.defaultColor, endColor)
    valueAnimator.duration = 300L
    valueAnimator.interpolator = DecelerateInterpolator(1.5f)
    valueAnimator.addUpdateListener { animation: ValueAnimator ->
        backgroundTintList = ColorStateList.valueOf(animation.animatedValue as Int)
    }
    valueAnimator.start()
}

fun BottomAppBar.setRoundedCorners() {
    val babBackgroundDrawable = background as MaterialShapeDrawable
    babBackgroundDrawable.shapeAppearanceModel = babBackgroundDrawable.shapeAppearanceModel
        .toBuilder()
        .setAllCorners(RoundedCornerTreatment())
        .setAllCornerSizes(RelativeCornerSize(0.5F))
        .build()
}

fun RecyclerView.setLinearManager(context: Context, isVertical: Boolean, stackFromEnd: Boolean = false) {
    val manager = LinearLayoutManager(
        context,
        if (isVertical) LinearLayoutManager.VERTICAL else LinearLayoutManager.HORIZONTAL,
        false
    )
    manager.stackFromEnd = stackFromEnd
    layoutManager = manager
}

fun RelativeLayout.setMargins(start: Int, top: Int, end: Int, bottom: Int) {
    val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    )
    params.setMargins(start, top, end, bottom)
    layoutParams = params
    requestLayout()
}

fun RecyclerView.Adapter<*>.disableAutoScrollToStart() {
    registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {

        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {

        }
    })
}

fun EditText.showKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.hideKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun ImageView.setTint(color: Int) {
    imageTintList = ColorStateList.valueOf(color)
}

fun ImageView.setDrawableString(imgString : String){
    val imageResourceId = context.resources.getIdentifier(imgString,"drawable",context.packageName)
    setImageResource(imageResourceId)
}

fun View.invisible(animate: Boolean = true) {
    if (animate) {
        clearAnimation()
        val centerX = width / 2f
        val centerY = height.toFloat() // Điểm chính giữa phía dưới của view

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(this, View.TRANSLATION_X, centerX - pivotX),
            ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, centerY - pivotY),
            ObjectAnimator.ofFloat(this, View.SCALE_X, 0f),
            ObjectAnimator.ofFloat(this, View.SCALE_Y, 0f),
            ObjectAnimator.ofFloat(this, View.ALPHA, 0f)
        )
        animatorSet.interpolator = AccelerateInterpolator()
        animatorSet.duration = 500L
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {

            }

            override fun onAnimationEnd(p0: Animator) {
                this@invisible.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(p0: Animator) {

            }

            override fun onAnimationRepeat(p0: Animator) {
            }
        })
        animatorSet.start()
    } else {
        this.visibility = View.INVISIBLE
    }
}

/**
 * Makes the view come back
 *
 * @param animate adds animation to the process
 */
fun View.visible(animate: Boolean) {
    if (animate) {
        clearAnimation()
        this.animate()
            .scaleY(1F)
            .scaleX(1F)
            .alpha(1F)
            .setInterpolator(LinearOutSlowInInterpolator())
            .setDuration(500L)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                    this@visible.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(p0: Animator) {

                }

                override fun onAnimationCancel(p0: Animator) {

                }

                override fun onAnimationRepeat(p0: Animator) {
                }
            })
            .start()
    } else {
        this.visibility = View.VISIBLE
    }
}

fun View.scrollUpAndVisible(animate: Boolean = true) {
    if (animate) {
        this.translationY = this.height.toFloat()
        this.visibility = View.VISIBLE

        val animator = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f)
        animator.interpolator = AccelerateInterpolator()
        animator.duration = 300L
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {}

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        animator.start()
    } else {
        this.visibility = View.VISIBLE
    }
}

fun View.scrollDownAndInvisible(animate: Boolean = true) {
    if (animate) {
        val animator = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, this.height.toFloat())
        animator.interpolator = AccelerateInterpolator()
        animator.duration = 300L
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                this@scrollDownAndInvisible.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        animator.start()
    } else {
        this.visibility = View.INVISIBLE
    }
}

fun View.slideUpAndDisappear(animate: Boolean = true) {
    if (animate) {

        this.translationY = 0f
        this.visibility = View.VISIBLE

        val animatorSet = AnimatorSet()
        val translationAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, -100f)
        val alphaAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 0f)

        animatorSet.playTogether(translationAnimator, alphaAnimator)
        animatorSet.interpolator = AccelerateInterpolator()
        animatorSet.duration = 300L
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                this@slideUpAndDisappear.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        animatorSet.start()
    } else {
        this.visibility = View.INVISIBLE
    }
}
fun View.slideDownAndAppear(animate: Boolean = true) {
    if (animate) {

        this.translationY = -100f
        this.visibility = View.VISIBLE

        val animatorSet = AnimatorSet()
        val translationAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f)
        val alphaAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 1f)
        animatorSet.playTogether(translationAnimator, alphaAnimator)
        animatorSet.interpolator = AccelerateInterpolator()
        animatorSet.duration = 300L
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {}

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        animatorSet.start()
    } else {
        this.visibility = View.VISIBLE
    }
}
