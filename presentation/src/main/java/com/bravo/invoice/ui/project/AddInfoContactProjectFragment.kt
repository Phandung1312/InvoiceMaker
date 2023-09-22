package com.bravo.invoice.ui.project

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.domain.model.ContactInfoProject
import com.bravo.invoice.databinding.AddInfoContactProject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddInfoContactProjectFragment :
    BaseFragment<AddInfoContactProject>(AddInfoContactProject::inflate) {
    companion object {
        const val DATA_INFO = "DATAINFO"
    }

    private val projectViewModel by viewModels<ProjectViewModel>()
    override fun initListeners() {
        binding.cancelTextView.clicks(withAnim = false) {
            popBackStack()
        }
        binding.saveTextView.clicks(withAnim = false) {
            val idProject = this.arguments?.getLong(ProjectContactFragment.DATA_ID)
            if (binding.givenNameEdt.text.isNotEmpty() || binding.surNameEdt.text.isNotEmpty()) {
                val dataContact = ContactInfoProject(
                    0,
                    idProject,
                    binding.givenNameEdt.text.toString(),
                    binding.surNameEdt.text.toString(),
                    binding.phoneEdt.text.toString(),
                    binding.emailEdt.text.toString(),
                    "Trader Runi"
                )
                projectViewModel.insertContact(dataContact)
                popBackStack()
            } else {
                binding.saveTextView.isEnabled = true
                binding.saveTextView.setTextColor(Color.BLUE)
            }
        }

    }

    override fun initView() {
        binding.givenNameEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.saveTextView.isEnabled = true
                binding.saveTextView.setTextColor(Color.BLUE)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.surNameEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.saveTextView.isEnabled = true
                binding.saveTextView.setTextColor(Color.BLUE)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }


}