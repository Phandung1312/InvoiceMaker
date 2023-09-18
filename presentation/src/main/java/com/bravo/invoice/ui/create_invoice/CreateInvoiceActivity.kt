package com.bravo.invoice.ui.create_invoice


import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bravo.basic.extensions.invisible
import com.bravo.basic.extensions.makeToast
import com.bravo.basic.extensions.scrollDownAndInvisible
import com.bravo.basic.extensions.scrollUpAndVisible
import com.bravo.basic.extensions.slideDownAndAppear
import com.bravo.basic.extensions.slideUpAndDisappear
import com.bravo.basic.extensions.visible
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.R
import com.bravo.invoice.adapter.InvoiceTemplateAdapter
import com.bravo.invoice.common.Preferences
import com.bravo.invoice.common.Utils
import com.bravo.invoice.databinding.ActivityCreateInvoiceBinding
import com.bravo.invoice.models.InvoiceDesign
import com.bravo.invoice.pdf.PdfManager
import com.bravo.invoice.ui.create_invoice.design_logo.DesignLogoFragment
import com.bravo.invoice.ui.create_invoice.select_template.TemplateFragment
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
        pref.isFirstDesign.set(true)
        binding.activity = this
        setNavController()
    }
    private fun setNavController(){
        try{

            binding.bottomNavigation.setOnItemSelectedListener { item ->
                val selectedFragment = when (item.itemId) {
                    R.id.template -> TemplateFragment()
                    R.id.logo -> DesignLogoFragment()
//                    R.id.color -> 3
//                    R.id.banner -> 4
//                    R.id.watermark -> 5
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
            val pdfManager = PdfManager(applicationContext, Utils.getSampleInvoice(), invoiceDesign.templateId, invoiceDesign.color)
            val bitmap = pdfManager.getImpactPdf()
            bitmap?.let{
                binding.ivTemplate.setImageBitmap(it)
            }
        }
    }
    fun onClose(){
        makeToast("Close")
    }
    fun onGotIt(){
        pref.isFirstDesign.set(false)
    }
    fun onPreview(){
        hideBottomLayout()
    }
    fun showBottomLayout(){
        binding.isVisible = true
    }
    fun hideBottomLayout(){
        binding.isVisible = false
    }
}