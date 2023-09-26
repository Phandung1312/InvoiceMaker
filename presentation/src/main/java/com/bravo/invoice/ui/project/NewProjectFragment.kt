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
    @Inject
    lateinit var projectAdapter: ProjectAdapter

    override fun initView() {
        super.initView()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding.newProject = this@NewProjectFragment
    }


    fun openAddNewProject() {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, AddNewProjectFragment()).commit()
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


    }

    override fun initListeners() {
        binding.viewComplete.clicks(withAnim = false) {
            binding.isVisibleView = true
            checkData(true)

        }
        binding.viewActive.clicks(withAnim = false) {
            binding.isVisibleView = false
            checkData(false)

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
            (requireActivity() as MainActivity).addFragment(fragment)


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