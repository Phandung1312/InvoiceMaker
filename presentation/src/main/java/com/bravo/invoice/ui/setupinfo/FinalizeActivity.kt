package com.bravo.invoice.ui.setupinfo


import android.animation.Animator
import com.bravo.basic.extensions.makeToast
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.common.Preferences
import com.bravo.invoice.databinding.ActivityFinalizeBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class FinalizeActivity : BaseActivity<ActivityFinalizeBinding>(ActivityFinalizeBinding::inflate) {
    @Inject lateinit var pref : Preferences
    override fun initListener() {
        binding.lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
            }

            override fun onAnimationEnd(p0: Animator) {
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
                makeToast("Done")
                binding.lottieAnimationView.pauseAnimation()
                val value = pref.businessInfo.get()
                Timber.e("Info:", value.tradingName)
            }
        })
    }
}