package com.bravo.invoice.ui.project

import android.R
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isGone
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
        const val DATA_CONTACT = "DATA_CONTACT"
        const val isEdit = "IS_EDIT"
    }

    private val dataInfo by lazy { arguments?.getSerializable(DATA_CONTACT) as? ContactInfoProject }
    val data = listOf(
        "Owner",
        "Supplier",
        "Tenant",
        "Building Manager",
        "Contractor",
        "Employee",
        "Realtor",
        "Accounts Payabl"
    )
    private var roleData = ""
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
                    roleData
                )
                val isEdit = arguments?.getBoolean(isEdit)
                if (isEdit == true) {
                    val dataUpdate = ContactInfoProject(
                        dataInfo!!.id,
                        idProject,
                        binding.givenNameEdt.text.toString(),
                        binding.surNameEdt.text.toString(),
                        binding.phoneEdt.text.toString(),
                        binding.emailEdt.text.toString(),
                        roleData
                    )
                    projectViewModel.updateContact(dataUpdate)
                    popBackStack()
                } else {
                    projectViewModel.insertContact(dataContact)
                    popBackStack()
                }


            } else {
                binding.saveTextView.isEnabled = true
                binding.saveTextView.setTextColor(Color.BLUE)
            }
        }
        binding.viewDelete.clicks(withAnim = false) {
            if (dataInfo != null) {
                projectViewModel.deleteContactInfo(dataInfo!!)
                popBackStack()
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

        if (dataInfo != null) {
            binding.viewChooseContactsProject.isGone = true
            binding.saveTextView.text = "Edit"
            binding.viewDelete.isGone = false
            binding.addClientTextView.text = "Edit Contact"
            binding.givenNameEdt.setText(dataInfo?.givenName)
            binding.surNameEdt.setText(dataInfo?.surName)
            binding.phoneEdt.setText(dataInfo?.mobileContact)
            binding.emailEdt.setText(dataInfo?.emailContact)
        } else {
            binding.viewChooseContactsProject.isGone = false
            binding.viewDelete.isGone = true
            binding.saveTextView.text = "Save"
            binding.addClientTextView.text = "Add Contact"
        }

        val adapter = ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerRole.adapter = adapter
        if (dataInfo != null) {
            val defaultSelection = dataInfo?.role
            val position = adapter.getPosition(defaultSelection)
            binding.spinnerRole.setSelection(position)
        }
        binding.spinnerRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = data[position]
                roleData = selectedItem

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }


}