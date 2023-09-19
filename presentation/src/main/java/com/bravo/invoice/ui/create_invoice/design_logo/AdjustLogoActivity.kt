package com.bravo.invoice.ui.create_invoice.design_logo


import androidx.lifecycle.lifecycleScope
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.common.AppPool
import com.bravo.invoice.common.Constants
import com.bravo.invoice.common.Preferences
import com.bravo.invoice.common.Utils
import com.bravo.invoice.databinding.ActivityAdjustLogoBinding
import com.bravo.invoice.dialogs.LoadingDialog
import com.bravo.invoice.models.AdditionalImageUI
import com.bravo.invoice.models.Invoice
import com.bravo.invoice.models.InvoiceDesign
import com.bravo.invoice.models.LogoUI
import com.bravo.invoice.pdf.PdfManager
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AdjustLogoActivity : BaseActivity<ActivityAdjustLogoBinding>(ActivityAdjustLogoBinding::inflate) {
    @Inject lateinit var appPool: AppPool
    @Inject lateinit var pref : Preferences

    private var logoUI = LogoUI()
    private var additionalImageUI = AdditionalImageUI()
    private val sizeClicks : Subject<Int> by lazy { BehaviorSubject.createDefault(0) }
    private val alignmentClicks : Subject<Int> by lazy { BehaviorSubject.createDefault(0) }

    override fun initView() {
        binding.activity = this@AdjustLogoActivity
        logoUI.bitmap = appPool.logo
        additionalImageUI.bitmap = appPool.logo
        createInvoicePdf(Utils.getSampleInvoice().copy(logo = logoUI, additionalImage = additionalImageUI ),pref.invoiceDesigned.get())
        binding.isVisibleAdjust = appPool.currentOption == DesignLogoFragment.LOGO
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
            val size  = when(index){
                0 -> Constants.SMALL_SIZE
                1 -> Constants.MEDIUM_SIZE
                2 -> Constants.LARGE_SIZE
                else -> Constants.MEDIUM_SIZE
            }
            if(appPool.currentOption == DesignLogoFragment.LOGO) logoUI.size = size
            else additionalImageUI.size = size
            createInvoicePdf(Utils.getSampleInvoice().copy(logo = logoUI),pref.invoiceDesigned.get())
        }
        alignmentClicks.autoDispose(scope()).subscribe { index ->
            binding.radioLeftAlignment.isSelected = index == 0
            binding.radioCenterAlignment.isSelected = index == 1
            binding.radioRightAlignment.isSelected = index == 2
            logoUI.alignment = when(index){
                0 -> Constants.ALIGNMENT_START
                1 -> Constants.ALIGNMENT_CENTER
                2 -> Constants.ALIGNMENT_RIGHT
                else -> Constants.ALIGNMENT_CENTER
            }
            createInvoicePdf(Utils.getSampleInvoice().copy(logo = logoUI),pref.invoiceDesigned.get())
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


    fun onSave(){
        lifecycleScope.launch(Dispatchers.Main) {
            val loadingDialog = LoadingDialog(this@AdjustLogoActivity )
            loadingDialog.show()
            val currentInvoiceDesign = pref.invoiceDesigned.get()
            pref.invoiceDesigned.set(currentInvoiceDesign.copy(logo = logoUI))
            delay(500)
            loadingDialog.dismiss()
            finish()
        }
    }

    fun onPreview(){
        binding.isVisible = false
    }
    fun showBottomLayout(){
        binding.isVisible = true
    }
    fun onClose(){
        finish()
    }
    private fun createInvoicePdf(invoice : Invoice,invoiceDesign: InvoiceDesign) {
        lifecycleScope.launch(Dispatchers.Main) {
            val pdfManager = PdfManager(applicationContext, invoice, invoiceDesign.templateId, invoiceDesign.color)
            val bitmap = pdfManager.getInvoicePDF()
            bitmap?.let{
                binding.ivTemplate.setImageBitmap(it)
            }
        }
    }
}