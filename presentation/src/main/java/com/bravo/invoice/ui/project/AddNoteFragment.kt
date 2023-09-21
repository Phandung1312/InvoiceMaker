package com.bravo.invoice.ui.project

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.AddNotes


class AddNoteFragment : BaseFragment<AddNotes>(AddNotes::inflate){
    override fun initListeners() {
       binding.closeNotes.clicks(withAnim = false) {

       }
        binding.saveButton.clicks(withAnim = false) {

        }
    }

    override fun initView() {
        binding.edtNotes.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.edtNotes, InputMethodManager.SHOW_IMPLICIT)
    }
}