package com.bravo.invoice.ui.project

import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.DetailProjectContacts
import com.bravo.invoice.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectContactFragment : BaseFragment<DetailProjectContacts>(DetailProjectContacts::inflate) {
    companion object {
        const val DATACONTACT = "DATACONTACT"
    }

    override fun initListeners() {
        binding.backTextView.clicks {
            popBackStack()
        }
    }

    override fun initData() {
        super.initData()
    }

    override fun initView() {
        val type: String? = requireArguments().getString(DATACONTACT)
        binding.nameClientTextView.text = type
    }
}