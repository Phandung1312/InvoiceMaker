package com.bravo.invoice.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import com.bravo.basic.extensions.clicks
import com.bravo.invoice.databinding.BottomSheetProjectStatusBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetProjectStatus : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetProjectStatusBinding

    var statusData = "Active"
    var result: (String) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetProjectStatusBinding.inflate(inflater, container, false)
        initData()
        initListener()
        return binding.root
    }

    private fun initData() {
        if (statusData == "Active") {
            binding.checkedActiveImg.isGone = false
        } else {
            binding.checkedCompleteImg.isGone = false
        }
    }


    private fun initListener() {
        binding.viewActive.clicks(withAnim = false) {
            result.invoke("Active")
            binding.checkedActiveImg.isGone = false
            binding.checkedCompleteImg.isGone = true
            dismiss()
        }
        binding.viewComplete.clicks(withAnim = false) {
            result.invoke("Complete")
            binding.checkedCompleteImg.isGone = false
            binding.checkedActiveImg.isGone = true
            dismiss()
        }
        binding.closeButton.clicks {
            dismiss()
        }
    }

}