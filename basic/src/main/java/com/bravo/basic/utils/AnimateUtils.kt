package com.bravo.basic.utils

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.view.isVisible
import com.bravo.basic.extensions.invisible
import com.bravo.basic.extensions.scrollUpAndVisible

class  AnimateUtils {
    companion object{
        const val SCALE_POPUP = 0
        const val SLIDE_UP_DOWN = 1
        const val SCROLL_UP_DOWN = 2

        private const val HEIGHT_SLIDE = 100f
        fun animateShowAndHide(view : View,isVisible: Boolean ,option : Int){
            when(option){
                0 -> {
                    scalePopup(view, isVisible)
                }
                1->{
                    slideUpDown(view, isVisible)
                }
                2->{
                    scrollUpDown(view, isVisible)
                }
            }
        }
        private fun slideUpDown(view: View, isVisible : Boolean) {
            view.translationY = if(isVisible) -HEIGHT_SLIDE else 0f
            view.visibility = View.VISIBLE

            val animatorSet = AnimatorSet()
            val translationAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, if(isVisible) 0f else -HEIGHT_SLIDE )
            val alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, if(isVisible) 1f else 0f)

            animatorSet.playTogether(translationAnimator, alphaAnimator)
            animatorSet.interpolator = AccelerateInterpolator()
            animatorSet.duration = 300L
            animatorSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    view.isVisible = isVisible
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })
            animatorSet.start()
        }

        private fun scrollUpDown(view : View, isVisible: Boolean){
            view.translationY = if(isVisible) view.height.toFloat() else 0f
            view.visibility = View.VISIBLE

            val animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, if(isVisible) 0f else view.height.toFloat() )
            animator.interpolator = AccelerateInterpolator()
            animator.duration = 300L
            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    view.isVisible = isVisible
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })
            animator.start()
        }

        private fun scalePopup(view : View, isVisible: Boolean){
            if(isVisible){
                view.isVisible = true
            }
            else{
                view.clearAnimation()
                val centerX = view.width / 2f
                val centerY = view.height.toFloat()

                val animatorSet = AnimatorSet()
                animatorSet.playTogether(
                    ObjectAnimator.ofFloat(view, View.TRANSLATION_X, centerX - view.pivotX),
                    ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, centerY - view.pivotY),
                    ObjectAnimator.ofFloat(view, View.SCALE_X, 0f),
                    ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f),
                    ObjectAnimator.ofFloat(view, View.ALPHA, 0f)
                )
                animatorSet.interpolator = AccelerateInterpolator()
                animatorSet.duration = 500L
                animatorSet.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator) {

                    }

                    override fun onAnimationEnd(p0: Animator) {
                        view.isVisible = isVisible
                    }

                    override fun onAnimationCancel(p0: Animator) {

                    }

                    override fun onAnimationRepeat(p0: Animator) {
                    }
                })
                animatorSet.start()
            }
        }
    }
}