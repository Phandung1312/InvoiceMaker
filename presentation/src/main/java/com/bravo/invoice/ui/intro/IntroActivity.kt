package com.bravo.invoice.ui.intro

import android.content.Intent
import android.text.Html
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.viewpager2.widget.ViewPager2
import com.bravo.basic.extensions.getDimens
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.R
import com.bravo.invoice.adapter.IntroduceAdapter
import com.bravo.invoice.databinding.ActivityIntroBinding
import com.bravo.invoice.models.Intro
import com.bravo.invoice.ui.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(ActivityIntroBinding::inflate) {
    @Inject lateinit var introduceAdapter: IntroduceAdapter
    var first = true
    override fun initView() {
        binding.introActivity = this
        binding.viewpager.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            this.adapter = introduceAdapter.apply {
                data = Intro.values().toList()
            }
        }
        addBottomDots(0)
    }

    fun onTry(){
        val intent = Intent(this , WelcomeActivity::class.java )
        startActivity(intent)
        finish()
    }
    private fun addBottomDots(currentPage : Int){
        binding.layoutDots.apply {
            if(childCount > 0) removeAllViewsInLayout()
        }
        val dotSize = binding.root.context.getDimens(com.intuit.ssp.R.dimen._12ssp)
        val dots = ArrayList<TextView>()
        for(i in 0 until introduceAdapter.itemCount){
            dots.add(TextView(this))
            dots[i].apply {
                text = Html.fromHtml("&#8226;", HtmlCompat.FROM_HTML_MODE_LEGACY)
                textSize = dotSize
                setTextColor(context.getColor(if(currentPage == i) R.color.dot_active else R.color.dot_inactive))
                binding.layoutDots.addView(dots[i])
            }
        }
    }
    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            if(!first){
                addBottomDots(position)
            }
            else{
                first = false
            }
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }

    override fun onResume() {
        binding.viewpager.registerOnPageChangeCallback(pageChangeCallback)
        super.onResume()
    }
    override fun onPause() {
        binding.viewpager.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onPause()
    }
}