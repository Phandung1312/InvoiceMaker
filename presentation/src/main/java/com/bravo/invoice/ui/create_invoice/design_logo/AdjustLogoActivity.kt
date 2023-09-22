package com.bravo.invoice.ui.create_invoice.design_logo


import androidx.lifecycle.lifecycleScope
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.common.pool.InvoicePool
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
import io.reactivex.subjects.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AdjustLogoActivity : BaseActivity<ActivityAdjustLogoBinding>(ActivityAdjustLogoBinding::inflate) {
    @Inject lateinit var invoicePool: InvoicePool
    @Inject lateinit var pref : Preferences

    private lateinit var invoiceDesign : InvoiceDesign
    private val sizeClicks : Subject<Int> by lazy { BehaviorSubject.create() }
    private val alignmentClicks : Subject<Int> by lazy { BehaviorSubject.create() }

    override fun initView() {
        binding.activity = this@AdjustLogoActivity
        binding.isVisibleAdjust = invoicePool.currentOption == DesignLogoFragment.LOGO
    }

    override fun initData() {
        invoiceDesign = pref.invoiceDesigned.get()
        invoiceDesign.logo.bitmap = invoicePool.logo
        invoiceDesign.additionalImageUI.bitmap = invoicePool.additionalImage
        invoiceDesign.logo.size = invoiceDesign.logo.size
        invoiceDesign.logo.alignment = invoiceDesign.logo.alignment
        invoiceDesign.additionalImageUI.size = invoiceDesign.additionalImageUI.size
        setSelection()
    }
    private fun setSelection(){
        val sizeIndex = when (if(invoicePool.currentOption == DesignLogoFragment.LOGO) invoiceDesign.logo.size else invoiceDesign.additionalImageUI.size){
            Constants.SMALL_SIZE -> 0
            Constants.MEDIUM_SIZE -> 1
            Constants.LARGE_SIZE ->2
            else -> 1
        }
        sizeClicks.onNext(sizeIndex)
        val alignmentIndex = when (invoiceDesign.logo.alignment){
            Constants.ALIGNMENT_START -> 0
            Constants.ALIGNMENT_CENTER -> 1
            Constants.ALIGNMENT_END ->2
            else -> 1
        }
        alignmentClicks.onNext(alignmentIndex)
        binding.switchHide.isChecked = invoiceDesign.hiddenCompanyName
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
    fun onHideCompanyName(isHide : Boolean){
        invoiceDesign.hiddenCompanyName = isHide
        createInvoicePdf(Utils.getSampleInvoice(),invoiceDesign)
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
            if(invoicePool.currentOption == DesignLogoFragment.LOGO) invoiceDesign.logo.size = size
            else invoiceDesign.additionalImageUI.size = size
            createInvoicePdf(Utils.getSampleInvoice(),invoiceDesign)
        }
        alignmentClicks.autoDispose(scope()).subscribe { index ->
            binding.radioLeftAlignment.isSelected = index == 0
            binding.radioCenterAlignment.isSelected = index == 1
            binding.radioRightAlignment.isSelected = index == 2
            invoiceDesign.logo.alignment = when(index){
                0 -> Constants.ALIGNMENT_START
                1 -> Constants.ALIGNMENT_CENTER
                2 -> Constants.ALIGNMENT_END
                else -> Constants.ALIGNMENT_CENTER
            }
            createInvoicePdf(Utils.getSampleInvoice(),invoiceDesign)
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
            pref.invoiceDesigned.set(invoiceDesign)
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
            val pdfManager = PdfManager(applicationContext, invoice.copy(
                logo = invoiceDesign.logo,
                banner = invoiceDesign.banner,
                additionalImage = invoiceDesign.additionalImageUI,
                watermark = invoiceDesign.watermark,
                hiddenCompanyName = invoiceDesign.hiddenCompanyName), invoiceDesign.templateId, invoiceDesign.color)
            val bitmap = pdfManager.getInvoicePDF()
            bitmap?.let{
                binding.ivTemplate.setImageBitmap(it)
            }
        }
    }
}