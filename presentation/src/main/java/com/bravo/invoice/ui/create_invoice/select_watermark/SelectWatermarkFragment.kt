package com.bravo.invoice.ui.create_invoice.select_watermark

import androidx.fragment.app.activityViewModels
import com.bravo.basic.extensions.tryOrNull
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.adapter.WatermarkAdapter
import com.bravo.invoice.common.Preferences
import com.bravo.invoice.databinding.SelectWatermarkClass
import com.bravo.invoice.models.Watermark
import com.bravo.invoice.ui.create_invoice.CreateInvoiceViewModel
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectWatermarkFragment : BaseFragment<SelectWatermarkClass>(SelectWatermarkClass::inflate) {
    private val viewModel by activityViewModels<CreateInvoiceViewModel>()
    @Inject lateinit var watermarkAdapter: WatermarkAdapter
    @Inject lateinit var pref : Preferences
    override fun initView() {
        binding.rvWatermarks.adapter = watermarkAdapter.apply {
            data = Watermark.values().toList()
            val invoiceDesign = pref.invoiceDesignedTemp.get()
            val selectedIndex = tryOrNull {
                data.indexOfFirst { invoiceDesign.watermark == it.watermarkId }
            } ?: 0
            setIndex(selectedIndex)
        }
    }

    override fun initListeners() {
        watermarkAdapter.itemClicks.autoDispose(scope()).subscribe { resourceId ->
            viewModel.setWatermark(if(resourceId == 0) null else resourceId)
        }
    }
}