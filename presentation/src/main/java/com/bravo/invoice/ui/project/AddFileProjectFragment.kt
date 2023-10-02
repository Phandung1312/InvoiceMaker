package com.bravo.invoice.ui.project


import android.os.Bundle
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.data.database.dao.ProjectDao
import com.bravo.domain.model.Project
import com.bravo.invoice.databinding.AddFileClass
import com.bravo.invoice.ui.main.MainActivity
import com.bravo.invoice.ui.project.adapter.AllFileAdapter
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddFileProjectFragment : BaseFragment<AddFileClass>(AddFileClass::inflate) {
    companion object {
        const val PROJECT_EXTRA = "PROJECT_EXTRA"
        const val PROJECT_DETAILS = "PROJECT_DETAILS"
    }
    private val projectViewModel by activityViewModels<ProjectViewModel>()
    @Inject lateinit var allFileAdapter: AllFileAdapter
    @Inject lateinit var projectDao: ProjectDao
    private val receivedProjectData by lazy { arguments?.getSerializable(PROJECT_EXTRA) as? Project }
    private val bottomSheetAddFile by lazy {
        BottomSheetAddFile()
    }
    fun addFile() {
        bottomSheetAddFile.setData(receivedProjectData!!.id, receivedProjectData!!.fileList)
        bottomSheetAddFile.show(childFragmentManager, bottomSheetAddFile.tag)
    }

    override fun initListeners() {
        binding.viewProjectContact.clicks(withAnim = false) {
            if (receivedProjectData != null) {
                val bundle = Bundle()
                val fragment = ProjectContactFragment()
                fragment.arguments = bundle
                bundle.putLong(ProjectContactFragment.DATA_ID, receivedProjectData!!.id)
                bundle.putString(
                    ProjectContactFragment.DATA_PROJECT,
                    receivedProjectData?.nameClient
                )
                (requireActivity() as MainActivity).addFragment(fragment)
            }

        }
        binding.closeImg.clicks(withAnim = false) {
            popBackStack()
        }
        binding.viewProjectDetails.clicks(withAnim = false) {
            val receivedProjectData = arguments?.getSerializable(PROJECT_EXTRA) as? Project
            if (receivedProjectData != null) {
                val bundle = Bundle()
                val fragment = ProjectDetailFragment()
                fragment.arguments = bundle
                bundle.putSerializable(PROJECT_DETAILS, receivedProjectData)
                (requireActivity() as MainActivity).addFragment(fragment)

            }
        }

        binding.viewNotes.clicks(withAnim = false) {
            val bundle = Bundle()
            val fragment = AddNoteFragment()
            fragment.arguments = bundle
            bundle.putLong(AddNoteFragment.getID, receivedProjectData!!.id)
            bundle.putString(AddNoteFragment.getNote, receivedProjectData?.notePrivate)
            addFragment(fragment)
        }
        binding.imgMenu.clicks(withAnim = false) {
            SweetAlertDialog(requireActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Delete Project")
                .setContentText("Are you sure you want to delete the project?")
                .setConfirmText("Delete!")
                .setConfirmClickListener { sDialog ->
                    projectViewModel.deleteProject(receivedProjectData!!)
                    sDialog.dismissWithAnimation()
                    popBackStack()
                }
                .setCancelButton(
                    "Cancel"
                ) { sDialog -> sDialog.dismissWithAnimation() }
                .show()
        }
        allFileAdapter.itemClickFile.autoDispose(scope()).subscribe { string ->
//            val bundle = Bundle()
//            val fragment = DetailFileFragment()
//            fragment.arguments = bundle
//            bundle.putString(DetailFileFragment.DATA_INFO,proje)
//            addFragment(fragment)


        }
    }

    override fun initView() {
        binding.fragment = this
        val receivedProjectData = arguments?.getSerializable(PROJECT_EXTRA) as? Project
        if (receivedProjectData != null) {
            binding.nameClient.text = receivedProjectData.nameClient
            binding.nameProjectTextview.text = receivedProjectData.nameProject
            projectDao.findById(receivedProjectData.id).observe(viewLifecycleOwner){ project ->
                project ?: return@observe
                binding.rvAllFile.apply {
                    adapter = allFileAdapter.apply {
                        binding.imgAddFile.isGone = true
                        data = project.fileList

                    }
                }
            }
        }

    }



}