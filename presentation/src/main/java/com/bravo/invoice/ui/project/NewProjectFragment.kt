package com.bravo.invoice.ui.project

import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.R
import com.bravo.invoice.databinding.NewProjectClass
import com.bravo.invoice.ui.main.MainActivity
import com.bravo.invoice.ui.project.adapter.ProjectAdapter
import com.google.android.material.card.MaterialCardView
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
            .add(R.id.fragment_container_view, AddNewProjectFragment())
            .commit()
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
    }

    override fun initListeners() {
        binding.viewComplete.clicks {
            binding.isVisibleView = true

        }
        binding.viewActive.clicks {
            binding.isVisibleView = false
        }
        binding.closeButton.clicks {
            popBackStack()
        }
        binding.addImg.clicks {
            (requireActivity() as MainActivity).addFragment(AddNewProjectFragment())
        }
    }
}