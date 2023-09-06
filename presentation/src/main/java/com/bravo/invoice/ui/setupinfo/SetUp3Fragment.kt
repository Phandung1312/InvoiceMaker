package com.bravo.invoice.ui.setupinfo

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.common.Preferences
import com.bravo.invoice.databinding.SetUp3Class
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetUp3Fragment : BaseFragment<SetUp3Class>(SetUp3Class::inflate) {
    private val viewModel by activityViewModels<SetUpInfoViewModel>()
    override fun initView() {
        binding.viewModel = viewModel

    }
}