package com.bravo.invoice.ui.project

import android.os.Bundle
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.domain.model.Project
import com.bravo.invoice.databinding.AddFileClass
import com.bravo.invoice.ui.main.MainActivity


class AddFileProjectFragment : BaseFragment<AddFileClass>(AddFileClass::inflate) {
    companion object {
        const val PROJECT_EXTRA = "PROJECT_EXTRA"
        const val PROJECT_DETAILS = "PROJECT_DETAILS"
    }


    override fun initListeners() {
        binding.viewProjectContact.clicks(withAnim = false) {
            val receivedProjectData = arguments?.getSerializable(PROJECT_EXTRA) as? Project
            val bundle = Bundle()
            val fragment = ProjectContactFragment()
            fragment.arguments = bundle
            bundle.putLong(ProjectContactFragment.DATA_ID, receivedProjectData!!.id)
            bundle.putString(ProjectContactFragment.DATA_PROJECT, receivedProjectData.nameClient)
            (requireActivity() as MainActivity).addFragment(fragment)
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
            val fragment = AddNoteFragment()
            (requireActivity() as MainActivity).addFragment(fragment)
        }
    }

    override fun initView() {
        val receivedProjectData = arguments?.getSerializable(PROJECT_EXTRA) as? Project
        if (receivedProjectData != null) {
            binding.nameClient.text = receivedProjectData.nameClient
            binding.nameProjectTextview.text = receivedProjectData.nameProject
        }
    }

    override fun initData() {

    }
}