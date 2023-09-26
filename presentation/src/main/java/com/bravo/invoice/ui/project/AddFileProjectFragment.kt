package com.bravo.invoice.ui.project

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.domain.model.Project
import com.bravo.invoice.databinding.AddFileClass
import com.bravo.invoice.ui.main.MainActivity
import com.bravo.invoice.ui.project.adapter.AllContactInfoAdapter
import com.bravo.invoice.ui.project.adapter.AllFileAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddFileProjectFragment : BaseFragment<AddFileClass>(AddFileClass::inflate) {
    companion object {
        const val PROJECT_EXTRA = "PROJECT_EXTRA"
        const val PROJECT_DETAILS = "PROJECT_DETAILS"
        const val PICK_IMAGE_REQUEST_CODE = 123
    }

    private val projectViewModel by viewModels<ProjectViewModel>()

    @Inject
    lateinit var allFileAdapter: AllFileAdapter
    private val receivedProjectData by lazy { arguments?.getSerializable(PROJECT_EXTRA) as? Project }
    private var uriImage: Uri? = null
    private val arrFile = ArrayList<String>()
    fun addFile() {
        val pickImageIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST_CODE)
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
                }
                .setCancelButton(
                    "Cancel"
                ) { sDialog -> sDialog.dismissWithAnimation() }
                .show()
        }
    }

    override fun initView() {
        binding.fragment = this
        val receivedProjectData = arguments?.getSerializable(PROJECT_EXTRA) as? Project
        if (receivedProjectData != null) {
            binding.nameClient.text = receivedProjectData.nameClient
            binding.nameProjectTextview.text = receivedProjectData.nameProject
        }

    }

    override fun initData() {
        binding.rvAllFile.apply {
            adapter = allFileAdapter.apply {
                data = arrFile
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val selectedImageUri = data.data
                arrFile.add(selectedImageUri.toString())
                allFileAdapter.notifyItemInserted(arrFile.size - 1)

            }
        }
    }
}