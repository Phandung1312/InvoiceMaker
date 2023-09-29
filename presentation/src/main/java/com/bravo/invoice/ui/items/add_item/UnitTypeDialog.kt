package com.bravo.invoice.ui.items.add_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bravo.invoice.R
import com.bravo.invoice.databinding.DialogUnitTypeBinding
import com.bravo.invoice.ui.items.add_item.AddItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnitTypeDialog : DialogFragment() {
    private lateinit var binding : DialogUnitTypeBinding
    private val viewModel by activityViewModels<AddItemViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogUnitTypeBinding.inflate(inflater, container, false)
        binding.apply {
            dialog = this@UnitTypeDialog
            viewModel = this@UnitTypeDialog.viewModel
        }
        return binding.root
    }

    fun onUnitTypeChanged(type : Int){
        viewModel.setUnitType(type)
        dismiss()
    }
    override fun onStart() {
        super.onStart()
        val layoutParams = dialog?.window?.attributes
        layoutParams?.width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        dialog?.window?.attributes = layoutParams
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog?.window?.setGravity(android.view.Gravity.BOTTOM)
    }
}