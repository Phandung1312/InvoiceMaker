package com.bravo.invoice.ui.create_invoice.select_banner

import androidx.fragment.app.activityViewModels
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.adapter.BannerAdapter
import com.bravo.invoice.common.Preferences
import com.bravo.invoice.databinding.SelectBannerClass
import com.bravo.invoice.models.Banner
import com.bravo.invoice.ui.create_invoice.CreateInvoiceViewModel
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectBannerFragment : BaseFragment<SelectBannerClass>(SelectBannerClass::inflate){
    private val viewModel by activityViewModels<CreateInvoiceViewModel>()
    @Inject lateinit var bannerAdapter: BannerAdapter
    @Inject lateinit var pref : Preferences
    override fun initView() {
        binding.rvBanners.adapter = bannerAdapter.apply {
            data = Banner.values().toList()
        }
    }

    override fun initListeners() {
        bannerAdapter.itemClicks.autoDispose(scope()).subscribe { resourceId ->
            viewModel.setBanner(if(resourceId == 0) 0 else resourceId)
        }
    }
}