package com.bravo.invoice.ui.create_invoice


import android.content.Intent
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bravo.basic.extensions.invisible
import com.bravo.basic.extensions.tryOrNull
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.R
import com.bravo.invoice.adapter.InvoiceTemplateAdapter
import com.bravo.invoice.common.Preferences
import com.bravo.invoice.common.Utils
import com.bravo.invoice.databinding.ActivityCreateInvoiceBinding
import com.bravo.invoice.models.InvoiceDesign
import com.bravo.invoice.pdf.PdfManager
import com.bravo.invoice.ui.create_invoice.design_logo.DesignLogoFragment
import com.bravo.invoice.ui.create_invoice.finalize_setup.FinalizeSetUpActivity
import com.bravo.invoice.ui.create_invoice.select_banner.SelectBannerFragment
import com.bravo.invoice.ui.create_invoice.select_color.SelectColorFragment
import com.bravo.invoice.ui.create_invoice.select_template.TemplateFragment
import com.bravo.invoice.ui.create_invoice.select_watermark.SelectWatermarkFragment
import com.uber.autodispose.android.lifecycle.autoDispose
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CreateInvoiceActivity : BaseActivity<ActivityCreateInvoiceBinding>(ActivityCreateInvoiceBinding::inflate) {
    private val viewModel by viewModels<CreateInvoiceViewModel>()
    @Inject
    lateinit var pref : Preferences

    @Inject
    lateinit var templateAdapter: InvoiceTemplateAdapter
    override fun initView() {
        binding.activity = this
        setNavController()
    }
    private fun setNavController(){
        try{

            binding.bottomNavigation.setOnItemSelectedListener { item ->
                val selectedFragment = when (item.itemId) {
                    R.id.template -> TemplateFragment()
                    R.id.logo -> DesignLogoFragment()
                    R.id.color -> SelectColorFragment()
                    R.id.banner -> SelectBannerFragment()
                    R.id.watermark -> SelectWatermarkFragment()
                    else -> null
                }
                if (selectedFragment != null) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, selectedFragment)
                        .commit()
                }
                true
            }
        }catch(e : Exception){

        }
        binding.bottomNavigation.selectedItemId = R.id.template
    }

    override fun initListener() {
        pref.invoiceDesignedTemp.asObservable().autoDispose(this).subscribe{
            viewModel.update(it)
        }
        viewModel.invoiceDesign.observe(this){ invoiceDesign ->
            createInvoicePdf(invoiceDesign)
        }
        templateAdapter.itemClicks.autoDispose(scope()).subscribe { template ->
            viewModel.setTemplate(template.id)
        }
        pref.isFirstDesign.asObservable().autoDispose(scope()).subscribe {
            if(it){
                binding.remindView.isVisible = true
            }
            else{
                binding.remindView.invisible(animate = true)
            }
        }
    }
    private fun createInvoicePdf(invoiceDesign: InvoiceDesign) {
        lifecycleScope.launch(Dispatchers.Main) {
            val pdfManager = PdfManager(applicationContext, Utils.getSampleInvoice().copy(), invoiceDesign)
            val bitmap = pdfManager.getInvoicePDF()
            bitmap?.let{
                binding.ivTemplate.setImageBitmap(it)
                ConstraintSet().apply {
                    clone(binding.templateLayout)
                    setDimensionRatio(binding.ivTemplate.id , "${bitmap.width}:${bitmap.height}")
                    applyTo(binding.templateLayout)
                }
            }
        }
    }
    fun onClose(){

    }
    fun onGotIt(){
        pref.isFirstDesign.set(false)
    }
    fun onNext(){
        val intent = Intent(this, FinalizeSetUpActivity::class.java)
        startActivity(intent)
        tryOrNull { overridePendingTransition(com.bravo.basic.R.anim.slide_in_left, com.bravo.basic.R.anim.nothing) }
        finish()
    }
    fun onPreview(){
        binding.isVisible = false
    }
    fun showBottomLayout(){
        binding.isVisible = true
    }
}