package com.bravo.invoice.ui.project

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.domain.model.Client
import com.bravo.domain.model.Project
import com.bravo.invoice.R
import com.bravo.invoice.databinding.NewProjectClass
import com.bravo.invoice.ui.client.DetailsClientFragment
import com.bravo.invoice.ui.main.MainActivity
import com.bravo.invoice.ui.project.adapter.ProjectAdapter
import com.google.android.material.card.MaterialCardView
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
            projectViewModel.getAllProjects.observe(viewLifecycleOwner) { it ->
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
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val index = viewHolder.adapterPosition
                showAlertConfirm(
                    projectAdapter.data[index].nameProject!!, projectAdapter.data[index], index
                )
            }
        }).attachToRecyclerView(binding.rvShowAllProject)
    }

    override fun initListeners() {
        binding.viewComplete.clicks {
            binding.isVisibleView = true

        }
        binding.viewActive.clicks {
            binding.isVisibleView = false
        }
        binding.closeButton.clicks {
            (requireActivity() as MainActivity).backFragment()
        }
        binding.addImg.clicks {
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
}