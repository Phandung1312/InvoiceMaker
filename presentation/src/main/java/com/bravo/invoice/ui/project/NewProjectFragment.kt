package com.bravo.invoice.ui.project

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.domain.model.Project
import com.bravo.invoice.R
import com.bravo.invoice.databinding.NewProjectClass
import com.bravo.invoice.ui.main.MainActivity
import com.bravo.invoice.ui.project.adapter.ProjectAdapter
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewProjectFragment : BaseFragment<NewProjectClass>(NewProjectClass::inflate) {
    private val projectViewModel by viewModels<ProjectViewModel>()
    private var isActive = true

    @Inject
    lateinit var projectAdapter: ProjectAdapter
    override fun initView() {
        super.initView()
        visibleBottomLayout(false)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding.newProject = this@NewProjectFragment
    }


    fun openAddNewProject() {
        addFragment(AddNewProjectFragment())
    }

    override fun initData() {
        binding.rvShowAllProject.apply {
            projectViewModel.getAllProjectActive.observe(viewLifecycleOwner) { it ->
                if (it.isEmpty()) {
                    binding.isVisible = true
                } else {
                    binding.isVisible = false
                    adapter = projectAdapter.apply {
                        data = it.reversed()
                    }
                }

            }
        }
        projectAdapter.changeText(true)
    }

    override fun initListeners() {
        binding.viewComplete.clicks(withAnim = false) {
            isActive = false
            binding.isVisibleView = true
            checkData(true)
            projectAdapter.changeText(false)
        }
        binding.viewActive.clicks(withAnim = false) {
            isActive = true
            binding.isVisibleView = false
            checkData(false)
            projectAdapter.changeText(true)

        }
        binding.closeButton.clicks(withAnim = false) {
            popBackStack()
        }
        binding.addImg.clicks(withAnim = false) {
            (requireActivity() as MainActivity).addFragment(AddNewProjectFragment())
        }
        projectAdapter.itemClicksProject.autoDispose(scope()).subscribe { project ->
            val bundle = Bundle()
            val fragment = AddFileProjectFragment()
            fragment.arguments = bundle
            bundle.putSerializable(AddFileProjectFragment.PROJECT_EXTRA, project)
            addFragment(fragment)
        }
        binding.ingChecked.clicks(withAnim = false) {
            val bundle = Bundle()
            val fragment = ScreenCheckListProject()
            fragment.arguments = bundle
            bundle.putBoolean(ScreenCheckListProject.DATA_LOAD, isActive)
            addFragment(fragment)
        }

        projectAdapter.callback = {
            projectViewModel.deleteProjectByID(it.first)
            projectAdapter.notifyItemChanged(it.second)

        }

        projectAdapter.callbackStatus = {
            if (isActive) {
                projectViewModel.updateStatus(it.first, "Complete")
                projectAdapter.notifyItemChanged(it.second)
            } else {
                projectViewModel.updateStatus(it.first, "Active")
                projectAdapter.notifyItemChanged(it.second)
            }

        }

    }

    private fun showAlertConfirm(titleData: String, project: Project, index: Int) {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setTitle("Delete $titleData?")
        alertDialogBuilder.setMessage("Deleting won't affect invoices or other documents that include these project.")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            projectViewModel.deleteProject(project)
            projectAdapter.notifyItemRemoved(index)
        }
        alertDialogBuilder.setNegativeButton("No") { _, _ ->
            projectAdapter.notifyItemChanged(index)
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun checkData(isTypeStatus: Boolean) {
        binding.rvShowAllProject.apply {
            if (isTypeStatus) {
                projectViewModel.getAllProjectComplete.observe(viewLifecycleOwner) { it ->
                    if (it.isEmpty()) {
                        binding.isVisible = true
                        binding.textAddCustomer.text = "Nothing complete yet"
                        binding.textDesCustomer.text =
                            "Finished a project? Change its status to complete so it appears here."
                    } else {
                        binding.isVisible = false
                        adapter = projectAdapter.apply {
                            data = it.reversed()
                        }
                    }
                }
            } else {
                projectViewModel.getAllProjectActive.observe(viewLifecycleOwner) { it ->
                    if (it.isEmpty()) {
                        binding.isVisible = true
                        binding.textAddCustomer.text = "Create a new project\nfor this client?"
                        binding.textDesCustomer.text =
                            "Keep your documents, photos and contacts\n for a project all together in one place."
                    } else {
                        binding.isVisible = false
                        adapter = projectAdapter.apply {
                            data = it.reversed()
                        }
                    }

                }
            }

        }

    }




}