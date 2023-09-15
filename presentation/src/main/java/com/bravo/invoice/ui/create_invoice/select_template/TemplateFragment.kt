package com.bravo.invoice.ui.create_invoice.select_template




import androidx.fragment.app.activityViewModels
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.adapter.InvoiceTemplateAdapter

import com.bravo.invoice.databinding.TemplateClass
import com.bravo.invoice.models.InvoiceTemplate
import com.bravo.invoice.ui.create_invoice.CreateInvoiceViewModel
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

@AndroidEntryPoint
class TemplateFragment : BaseFragment<TemplateClass>(TemplateClass::inflate) {
    private val viewModel by activityViewModels<CreateInvoiceViewModel>()
    @Inject
    lateinit var templateAdapter: InvoiceTemplateAdapter
    override fun initView() {
        binding.templateFragment = this
        binding.rvTemplate.adapter = templateAdapter.apply {
            data = InvoiceTemplate.values().toList()
            setIndex(viewModel.invoiceDesign.value!!.templateId)
        }
    }

    override fun initListeners() {
        templateAdapter.itemClicks.autoDispose(scope()).subscribe { template ->
            viewModel.setTemplate(template.id)
        }
    }
}
