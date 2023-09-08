package com.bravo.invoice.ui.create_invoice.design_logo


import androidx.appcompat.widget.AppCompatImageView
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.databinding.ActivityAdjustLogoBinding
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject


class AdjustLogoActivity : BaseActivity<ActivityAdjustLogoBinding>(ActivityAdjustLogoBinding::inflate) {
    private val sizeClicks : Subject<Int> by lazy { BehaviorSubject.createDefault(0) }
    private val alignmentClicks : Subject<Int> by lazy { BehaviorSubject.createDefault(0) }

    override fun initView() {
        binding.activity = this@AdjustLogoActivity
    }

    override fun initListener() {
        sizeGroup.forEachIndexed { index, size ->
            size.clicks {
                sizeClicks.onNext(index)
            }
        }
        alignmentGroup.forEachIndexed { index, alignment ->
            alignment.clicks {
                alignmentClicks.onNext(index)
            }
        }
    }
    override fun initObservable() {
        sizeClicks.autoDispose(scope()).subscribe { index ->
            binding.radioSmall.isSelected = index == 0
            binding.radioMedium.isSelected = index == 1
            binding.radioLarge.isSelected = index == 2
        }
        alignmentClicks.autoDispose(scope()).subscribe { index ->
            binding.radioLeftAlignment.isSelected = index == 0
            binding.radioCenterAlignment.isSelected = index == 1
            binding.radioRightAlignment.isSelected = index == 2
        }
    }
    fun onSizeChanged(index : Int){
        sizeClicks.onNext(index)
    }
    fun onAlignmentChanged(index : Int){
        alignmentClicks.onNext(index)
    }
    private val sizeGroup by lazy {
        listOf(
            binding.radioSmall,
            binding.radioMedium,
            binding.radioLarge
        )
    }

    private val alignmentGroup by lazy {
        listOf(
            binding.radioLeftAlignment,
            binding.radioCenterAlignment,
            binding.radioRightAlignment
        )
    }
}