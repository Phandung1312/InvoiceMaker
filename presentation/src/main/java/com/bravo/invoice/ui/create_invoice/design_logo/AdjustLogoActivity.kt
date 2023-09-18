package com.bravo.invoice.ui.create_invoice.design_logo


import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.lifecycleScope
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.common.AppPool
import com.bravo.invoice.common.Preferences
import com.bravo.invoice.common.Utils
import com.bravo.invoice.databinding.ActivityAdjustLogoBinding
import com.bravo.invoice.models.Invoice
import com.bravo.invoice.models.InvoiceDesign
import com.bravo.invoice.models.LogoUI
import com.bravo.invoice.pdf.PdfManager
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AdjustLogoActivity : BaseActivity<ActivityAdjustLogoBinding>(ActivityAdjustLogoBinding::inflate) {
    @Inject lateinit var appPool: AppPool
    @Inject lateinit var pref : Preferences

    private var logoUI = LogoUI()
    private val sizeClicks : Subject<Int> by lazy { BehaviorSubject.createDefault(0) }
    private val alignmentClicks : Subject<Int> by lazy { BehaviorSubject.createDefault(0) }

    override fun initView() {
        binding.activity = this@AdjustLogoActivity
        logoUI.bitmap = appPool.logo
        createInvoicePdf(Utils.getSampleInvoice().copy(logo = logoUI),pref.invoiceDesigned.get())
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
    private fun createInvoicePdf(invoice : Invoice,invoiceDesign: InvoiceDesign) {
        lifecycleScope.launch(Dispatchers.Main) {
            val pdfManager = PdfManager(applicationContext, invoice, invoiceDesign.templateId, invoiceDesign.color)
            val bitmap = pdfManager.getImpactPdf()
            bitmap?.let{
                binding.ivTemplate.setImageBitmap(it)
            }
        }
    }
}