package com.bravo.invoice.ui.project

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.DatePicker
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.domain.model.Project
import com.bravo.invoice.databinding.ProjectDetail
import com.bravo.invoice.ui.client.ClientViewModel
import com.bravo.invoice.ui.main.MainActivity
import com.bravo.invoice.ui.project.adapter.AddLocationAdapter
import com.bravo.invoice.ui.setupinfo.EnterAddressBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import java.util.Calendar
import javax.inject.Inject


@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class ProjectDetailFragment : BaseFragment<ProjectDetail>(ProjectDetail::inflate) {
    private val receivedData by lazy { arguments?.getSerializable(AddFileProjectFragment.PROJECT_DETAILS) as? Project }

    @Inject
    lateinit var addLocationAdapter: AddLocationAdapter
    private val projectViewModel by viewModels<ProjectViewModel>()
    private var checkData = "Active"
    private val bottomSheetStatus by lazy { BottomSheetProjectStatus() }
    private val bottomSheetLocation by lazy {
        EnterAddressBottomSheet("") { address ->
            addLocationAdapter.data = when {
                isAddedData -> ArrayList(addLocationAdapter.data).apply {
                    if (address.isNotEmpty()) {
                        add(address)
                    }

                }

                else -> ArrayList(receivedData!!.locationList).apply {
                    if (address.isNotEmpty()) {
                        add(address)
                    }
                }
            }
            isAddedData = true
            addLocationAdapter.notifyDataSetChanged()
        }
    }
    private var isAddedData = false

    override fun initListeners() {

        binding.backTextView.clicks(withAnim = false) {
            popBackStack()
        }
        binding.viewSetStatus.clicks(withAnim = false) {
            bottomSheetStatus.apply {
                this.statusData = checkData
                this.result = { result ->
                    binding.textStatus.text = result
                    checkData = result
                    if (result != receivedData?.status) {
                        binding.saveTextView.isEnabled = true
                        binding.saveTextView.setTextColor(Color.BLUE)
                    } else {
                        binding.saveTextView.isEnabled = false
                        binding.saveTextView.setTextColor(Color.GRAY)
                    }
                }
            }
            bottomSheetStatus.show(childFragmentManager, bottomSheetStatus.tag)
        }
        binding.saveTextView.clicks(withAnim = false) {
            if (receivedData != null) {
                val data = addLocationAdapter.data
                val projectData = Project(
                    receivedData!!.id,
                    receivedData?.nameClient,
                    binding.nameProject.text.toString(),
                    receivedData?.dateProject,
                    binding.startDateTextview.text.toString(),
                    binding.endDateTextview.text.toString(),
                    data,
                    binding.notesEdt.text.toString(),
                    binding.textStatus.text.toString()
                )
                if (binding.nameProject.text.toString() != receivedData?.nameProject
                    || binding.textStatus.text.toString() != receivedData?.status
                    || binding.notesEdt.text.toString() != receivedData?.note
                    || data != receivedData?.locationList
                    || binding.startDateTextview.text.toString() != receivedData?.dateStart
                    || binding.endDateTextview.text.toString() != receivedData?.dateEnd
                ) {
                    projectViewModel.updateProject(projectData)
                    popBackStack()
                } else {
                    binding.saveTextView.isEnabled = false
                    binding.saveTextView.setTextColor(Color.GRAY)
                }
            }

        }
        binding.viewClickLocation.clicks(withAnim = false) {
            bottomSheetLocation.show(childFragmentManager, bottomSheetLocation.tag)
        }
    }

    override fun initData() {
        visibleBottomLayout(false)
        binding.nameProject.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.saveTextView.isEnabled = true
                binding.saveTextView.setTextColor(Color.BLUE)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.notesEdt.addTextChangedListener(object : TextWatcher {
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

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun initView() {
        val receivedData =
            arguments?.getSerializable(AddFileProjectFragment.PROJECT_DETAILS) as? Project
        if (receivedData != null) {
            binding.nameProject.setText(receivedData.nameProject)
            binding.notesEdt.setText(receivedData.note)
            binding.rvListLocation.apply {
                adapter = addLocationAdapter.apply {
                    data = receivedData.locationList
                }
            }
            Log.e("Quang",receivedData.dateStart.toString())
            receivedData.dateStart?.let { convertStringToDataPicker(it, binding.datePickerStart) }
            receivedData.dateEnd?.let { convertStringToDataPicker(it, binding.datePickerEnd) }

        }
        binding.datePickerStart.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val formattedDate = dateFormat.format(calendar.time)
            binding.startDateTextview.text = formattedDate
        }

        binding.datePickerEnd.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val formattedDate = dateFormat.format(calendar.time)
            binding.endDateTextview.text = formattedDate
        }


    }


    private fun convertStringToDataPicker(dataTime: String, pikerDateTime: DatePicker) {
        val pattern = "yyyy-MM-dd"
        val formatter = DateTimeFormatter.ofPattern(pattern)
        try {
            val dateTime = LocalDateTime.parse(dataTime, formatter)
            val day = dateTime.dayOfMonth
            val month = dateTime.monthValue
            val year = dateTime.year
            pikerDateTime.init(year, month, day, null)
        } catch (e: Exception) {
            println("Error parsing the input string: ${e.message}")
        }
    }


}