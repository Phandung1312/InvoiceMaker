package com.bravo.invoice.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bravo.basic.extensions.clicks
import com.bravo.invoice.databinding.DialogInformationBinding

class InformationDialog(
    private val title : String,
    private val content : String,
    private val confirmText : String,
) : DialogFragment() {
    private lateinit var binding : DialogInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogInformationBinding.inflate( inflater, container, false)
        binding.apply {
            tvTitle.text = title
            tvContent.text = content
            tvConfirm.text = confirmText
            tvConfirm.clicks(withAnim = false) {
                dismiss()
            }
        }
        return binding.root
    }
}