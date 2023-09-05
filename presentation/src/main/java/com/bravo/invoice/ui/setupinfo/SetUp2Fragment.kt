package com.bravo.invoice.ui.setupinfo

import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.SetUp2Class

class SetUp2Fragment : BaseFragment<SetUp2Class>(SetUp2Class::inflate) {

    override fun initView() {
        binding.setUp2Fragment = this
    }
    fun onEnterAddress(){
        val bottomSheet = EnterAddressBottomSheet{ address ->
            binding.tvBusinessAddress.text = address
        }
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }
}