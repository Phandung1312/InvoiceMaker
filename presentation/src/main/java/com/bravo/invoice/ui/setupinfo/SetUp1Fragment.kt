package com.bravo.invoice.ui.setupinfo

import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.SetUp1Class

class SetUp1Fragment : BaseFragment<SetUp1Class>(SetUp1Class::inflate) {
    override fun initView() {
        binding.fragment = this
    }
    fun onEnterAddress(){
        val bottomSheet = EnterAddressBottomSheet{ address ->
            binding.tvBusinessAddress.text = address
        }
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }
}