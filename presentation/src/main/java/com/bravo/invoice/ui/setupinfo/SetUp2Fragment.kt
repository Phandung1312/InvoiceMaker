package com.bravo.invoice.ui.setupinfo

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.SetUp2Class
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetUp2Fragment : BaseFragment<SetUp2Class>(SetUp2Class::inflate) {
    private val viewModel by activityViewModels<SetUpInfoViewModel>()
    override fun initView() {
        binding.setUp2Fragment = this
        binding.viewModel = viewModel
    }
    fun onEnterAddress(){
        binding.root.clearFocus()
        val bottomSheet = EnterAddressBottomSheet(binding.tvBusinessAddress.text.toString()){ address ->
            binding.tvBusinessAddress.text = address
        }
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }
}