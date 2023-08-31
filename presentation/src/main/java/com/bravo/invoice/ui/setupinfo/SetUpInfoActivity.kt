package com.bravo.invoice.ui.setupinfo

import androidx.viewpager2.widget.ViewPager2
import com.bravo.basic.view.BaseActivity
import com.bravo.basic.view.BasePageAdapter
import com.bravo.invoice.R
import com.bravo.invoice.databinding.ActivitySetUpInfoBinding
import com.google.android.material.card.MaterialCardView

class SetUpInfoActivity : BaseActivity<ActivitySetUpInfoBinding>(ActivitySetUpInfoBinding::inflate) {
    private val fragments by lazy { listOf(SetUp1Fragment(), SetUp2Fragment(), SetUp3Fragment()) }
    private lateinit var stepBarList : ArrayList<MaterialCardView>
    override fun initView() {
        binding.setUpInfoActitvity = this
        stepBarList = arrayListOf(
            binding.cardStep1,
            binding.cardStep2,
            binding.cardStep3
        )
        binding.viewpager.apply {
            this.adapter = BasePageAdapter(this@SetUpInfoActivity).apply {
                    addFragments(*fragments.toTypedArray())
            }
            this.offscreenPageLimit = adapter?.itemCount ?: 1
            this.isUserInputEnabled = false
        }
        
        addStepProgressBar(0)

    }
    fun onNext(){
        binding.viewpager.currentItem += 1
    }
    fun onBack(){
        binding.viewpager.currentItem -= 1
    }

    private val pageChangeCallback by lazy {
        object: ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                addStepProgressBar(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        }
    }
    private fun  addStepProgressBar(currentPage : Int){
        stepBarList.forEachIndexed{ index,stepBar ->
            stepBar.setCardBackgroundColor(stepBar.context.getColor(if(currentPage >= index) R.color.blue_button else R.color.background_step_bar))
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