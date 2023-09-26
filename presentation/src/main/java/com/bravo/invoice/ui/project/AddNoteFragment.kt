package com.bravo.invoice.ui.project


import androidx.fragment.app.viewModels
import com.bravo.basic.extensions.clicks
import com.bravo.basic.extensions.showKeyboard
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.AddNotes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<AddNotes>(AddNotes::inflate) {
    companion object {
        const val getID = "GET_ID"
        const val getNote = "GET_NOTE"
    }

    private val projectViewModel by viewModels<ProjectViewModel>()
    override fun initListeners() {
        binding.closeNotes.clicks(withAnim = false) {
            popBackStack()
        }
        binding.saveButton.clicks(withAnim = false) {
            val dataNote = binding.edtNotes.text.toString()
            if (dataNote.isNotEmpty()) {
                val id = arguments?.getLong(getID)
                if (id != null) {
                    projectViewModel.updatePrivateNote(id, dataNote)
                }

            }
        }
    }

    override fun initData() {
        val note = arguments?.getString(getNote)
        binding.edtNotes.setText(note)
    }

    override fun initView() {
        binding.edtNotes.requestFocus()
        binding.edtNotes.showKeyboard()
    }
}