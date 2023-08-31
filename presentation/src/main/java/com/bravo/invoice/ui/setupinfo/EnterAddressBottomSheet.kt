package com.bravo.invoice.ui.setupinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bravo.invoice.databinding.BottomSheetEnterAddressBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EnterAddressBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetEnterAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = BottomSheetEnterAddressBinding.inflate(inflater, container, false)

        return binding.root
    }
}