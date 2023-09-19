package com.bravo.invoice.ui.more

import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.R
import com.bravo.invoice.databinding.MoreClass
import com.bravo.invoice.databinding.NewProjectClass
import com.bravo.invoice.ui.client.AddClientFragment
import com.bravo.invoice.ui.project.AddNewProjectFragment
import com.bravo.invoice.ui.project.NewProjectFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : BaseFragment<MoreClass>(MoreClass::inflate) {
    override fun initView() {
    }

    override fun initListeners() {
        binding.viewWorkFlow.clicks {
           requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, NewProjectFragment())
                .commit()
        }
    }


}