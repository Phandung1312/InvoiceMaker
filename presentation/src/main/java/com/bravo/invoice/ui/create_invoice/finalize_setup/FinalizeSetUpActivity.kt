package com.bravo.invoice.ui.create_invoice.finalize_setup


import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.R
import com.bravo.invoice.databinding.ActivityFinalizeSetUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FinalizeSetUpActivity : BaseActivity<ActivityFinalizeSetUpBinding>(ActivityFinalizeSetUpBinding::inflate) {
    private var currentStep = 1
    private val stepBarLists by lazy {
        listOf(
            binding.step1View,
            binding.step2View,
            binding.step3View,
            binding.step4View,
            binding.step5View
        )
    }
    override fun initView() {
        val fragment = DesignInvoiceStep2Fragment()
        translateFragment(fragment)
    }

    override fun translateFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(com.bravo.basic.R.anim.slide_in_left, com.bravo.basic.R.anim.slide_out_right)
            .replace(R.id.fragment_container_view, fragment)
            .commit()
        if(fragment is DesignInvoiceStep5Fragment) currentStep = 4
        setStepBar()
    }
    fun onFinalize(){

    }
    private fun setStepBar(){
        currentStep++
        stepBarLists.forEachIndexed { index, view ->
            view.setCardBackgroundColor(this.getColor((if(index < currentStep) R.color.blue_button else R.color.background_step_bar)))
        }
    }
}
