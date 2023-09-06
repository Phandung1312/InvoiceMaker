package com.bravo.invoice.ui.setupinfo

import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.SetUp1Class
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetUp1Fragment : BaseFragment<SetUp1Class>(SetUp1Class::inflate) {
    private val viewModel by activityViewModels<SetUpInfoViewModel>()
    override fun initView() {
        binding.fragment = this
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