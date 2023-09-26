package com.bravo.invoice.ui.project

import android.os.Bundle
import androidx.core.view.isGone
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.domain.model.ContactInfoProject
import com.bravo.invoice.databinding.ViewInfoDetailContact
import com.bravo.invoice.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowDetailInfoContactFragment :
    BaseFragment<ViewInfoDetailContact>(ViewInfoDetailContact::inflate) {
    private val dataInfo by lazy { arguments?.getSerializable(contactInfo) as? ContactInfoProject }

    companion object {
        const val contactInfo = "CONTACT_INFO"
    }

    override fun initView() {
        val dataInfo = arguments?.getSerializable(contactInfo) as? ContactInfoProject
        if (dataInfo != null) {
            binding.nameTextview.text = dataInfo.givenName.plus(dataInfo.surName)
            binding.roleTextview.text = dataInfo.role
            if (dataInfo.mobileContact!!.isNotEmpty() || dataInfo.emailContact!!.isNotEmpty()) {
                binding.viewMobile.isGone = false
                binding.viewEmail.isGone = false
                binding.mobileTextview.text = dataInfo.mobileContact
                binding.emailTextview.text = dataInfo.emailContact
            } else {
                binding.viewMobile.isGone = true
                binding.viewEmail.isGone = true
            }
        }
    }

    override fun initListeners() {
        binding.imgBack.clicks(withAnim = false) {
            popBackStack()
        }
        binding.editTextView.clicks(withAnim = false) {
            val bundle = Bundle()
            val fragment = AddInfoContactProjectFragment()
            fragment.arguments = bundle
            bundle.putSerializable(AddInfoContactProjectFragment.DATA_CONTACT, dataInfo)
            bundle.putBoolean(AddInfoContactProjectFragment.isEdit, true)
            addFragment(fragment)

        }
    }
}