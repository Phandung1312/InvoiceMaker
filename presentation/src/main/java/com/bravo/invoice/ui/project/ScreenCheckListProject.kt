package com.bravo.invoice.ui.project

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.domain.model.Project
import com.bravo.invoice.databinding.ScreenListChecked
import com.bravo.invoice.ui.project.adapter.AdapterCheckedProject
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import java.util.Objects
import javax.inject.Inject

@AndroidEntryPoint
class ScreenCheckListProject : BaseFragment<ScreenListChecked>(ScreenListChecked::inflate) {
    companion object {
        const val DATA_LOAD = "DATA_LOAD"
    }

    private val projectViewModel by viewModels<ProjectViewModel>()
    private val receivedData by lazy { arguments?.getBoolean(DATA_LOAD) }
    private var projectList = mutableListOf<Project>()

    @Inject
    lateinit var projectAdapter: AdapterCheckedProject
    override fun initView() {

    }

    override fun initData() {
        binding.rvShowAllProject.apply {
            if (receivedData != null) {
                if (receivedData == true) {
                    projectViewModel.getAllProjectActive.observe(viewLifecycleOwner) { it ->
                        adapter = projectAdapter.apply {
                            data = it.reversed()
                        }
                    }
                } else {
                    projectViewModel.getAllProjectComplete.observe(viewLifecycleOwner) { it ->
                        adapter = projectAdapter.apply {
                            data = it.reversed()
                        }
                    }
                }
            }
        }
        projectAdapter.itemClicksProject.autoDispose(scope())
            .subscribe {
                if (it.isChecked) {
                    binding.deleteView.isEnabled = false
                    binding.typeActiveOrComplete.isEnabled = false
                    projectList.remove(it)
                } else {
                    binding.deleteView.isEnabled = true
                    binding.typeActiveOrComplete.isEnabled = true
                    projectList.add(it)
                }
            }


        if (receivedData != null && receivedData == true) {
            binding.typeActiveOrComplete.text = "Complete"
        } else {
            binding.typeActiveOrComplete.text = "ReActive"
        }



    }



    override fun initListeners() {
        binding.deleteView.clicks(withAnim = false) {
            projectList.forEach {
                projectViewModel.deleteProject(it)
                projectAdapter.notifyDataSetChanged()
            }
        }
        binding.typeActiveOrComplete.clicks(withAnim = false) {
            if (receivedData == true) {
                projectList.forEach {
                    projectViewModel.updateStatus(it.id, "Complete")
                    projectAdapter.notifyDataSetChanged()
                }
            } else {
                projectList.forEach {
                    projectViewModel.updateStatus(it.id, "Active")
                    projectAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}