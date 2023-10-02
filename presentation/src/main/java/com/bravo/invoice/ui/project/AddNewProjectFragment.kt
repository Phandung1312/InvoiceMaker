package com.bravo.invoice.ui.project


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import com.bravo.basic.extensions.clicks
import com.bravo.basic.extensions.hideKeyboard
import com.bravo.basic.view.BaseFragment
import com.bravo.domain.model.Project
import com.bravo.invoice.adapter.ClientAdapter
import com.bravo.invoice.databinding.AddProjectClass
import com.bravo.invoice.ui.project.adapter.AddLocationAdapter
import com.bravo.invoice.ui.setupinfo.EnterAddressBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class AddNewProjectFragment : BaseFragment<AddProjectClass>(AddProjectClass::inflate) {
    private val projectViewModel by viewModels<ProjectViewModel>()
    private var checkDataClient: String? = null

    @Inject
    lateinit var clientAdapter: ClientAdapter

    @Inject
    lateinit var addLocationAdapter: AddLocationAdapter
    private val arrLocation = ArrayList<String>()
    private val arrFile = ArrayList<String>()

    private val bottomSheetFragment by lazy {
        BottomSheetClients { client ->
            checkDataClient = client.billingName.toString()
            binding.nameClientTextView.text = client.billingName
            if (client.billingName!!.isEmpty()) {
                binding.nameClientTextView.setTextColor(Color.RED)
            } else {
                binding.nameClientTextView.setTextColor(Color.BLACK)
            }
        }.apply {
            this.clientAdapter = this@AddNewProjectFragment.clientAdapter
        }
    }

    private val bottomSheetLocation by lazy {
        EnterAddressBottomSheet("") { address ->
            arrLocation.add(address)
            addLocationAdapter.data = arrLocation
            addLocationAdapter.notifyDataSetChanged()
            if (arrLocation.isEmpty()) {
                binding.locationTextView.isGone = false
                binding.rightImg.isGone = false
                binding.viewAddAnotherLocation.isGone = true
            } else {
                binding.locationTextView.isGone = true
                binding.rightImg.isGone = true
                binding.viewAddAnotherLocation.isGone = false
            }
        }
    }

    override fun initData() {
        binding.rvListLocation.apply {
            adapter = addLocationAdapter.apply {
                if (arrLocation.isNotEmpty()) {
                    binding.viewAddAnotherLocation.isGone = false
                    binding.rightImg.isGone = true
                    binding.locationTextView.isGone = true
                    data = arrLocation
                }
            }
        }
    }


    override fun initView() {
    }

    @SuppressLint("SimpleDateFormat")
    override fun initListeners() {
        binding.viewAddProject.clicks(withAnim = false) {
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }

        binding.viewClickLocation.clicks(withAnim = false) {
            bottomSheetLocation.show(childFragmentManager, bottomSheetLocation.tag)
        }
        binding.saveTextView.clicks(withAnim = false) {
            if (checkDataClient?.isEmpty() != false) {
                binding.nameClientTextView.setTextColor(Color.RED)
            } else {
                binding.nameClientTextView.setTextColor(Color.BLACK)
                val nameClient = binding.nameClientTextView.text.toString()
                val nameProject = binding.projectEdt.text.toString()
                val arrLocation = arrLocation
                val dateFormat = SimpleDateFormat("MMMM/dd", Locale.ENGLISH)
                val currentDate = dateFormat.format(Date())
                val dataProject = Project(
                    0,
                    nameClient,
                    nameProject,
                    currentDate,
                    "",
                    "",
                    arrLocation,
                    "", "",
                    "Active", arrFile
                )
                projectViewModel.insertProjects(dataProject)
                requireActivity().hideKeyboard()
                val bundle = Bundle()
                val fragment = AddFileProjectFragment()
                fragment.arguments = bundle
                bundle.putSerializable(AddFileProjectFragment.PROJECT_EXTRA, dataProject)
                addFragment(fragment)
            }
        }
        binding.cancelTextView.clicks(withAnim = false) {
            popBackStack()
        }

    }

    override fun onResume() {
        super.onResume()
//        initObservable()
    }

//    private fun initObservable() {
//        bottomSheetFragment.subject
//            .autoDispose(scope())
//            .subscribe {
//                when(it){
//                    bottomSheetFragment.addId->{
//                        addFragment()
//                    }
//                }
//            }
//    }

}