package com.bravo.invoice.ui.create_invoice.select_color

import androidx.fragment.app.activityViewModels
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.adapter.ColorAdapter
import com.bravo.invoice.databinding.SelectColorClass
import com.bravo.invoice.models.Color
import com.bravo.invoice.ui.create_invoice.CreateInvoiceViewModel
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectColorFragment : BaseFragment<SelectColorClass>(SelectColorClass::inflate) {
    private val viewModel by activityViewModels<CreateInvoiceViewModel>()
    @Inject lateinit var colorAdapter: ColorAdapter
    override fun initView() {
        binding.rvColors.adapter = colorAdapter.apply {
            data = Color.values().toList()
        }
    }

    override fun initListeners() {
        colorAdapter.itemClicks.autoDispose(scope()).subscribe {
            viewModel.setColor(it)
        }
    }
}