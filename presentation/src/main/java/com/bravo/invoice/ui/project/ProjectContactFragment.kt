package com.bravo.invoice.ui.project

import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bravo.basic.extensions.clicks
import com.bravo.basic.extensions.visible
import com.bravo.basic.view.BaseFragment
import com.bravo.data.database.dao.ProjectDao
import com.bravo.invoice.databinding.DetailProjectContacts
import com.bravo.invoice.ui.client.DetailsClientFragment
import com.bravo.invoice.ui.main.MainActivity
import com.bravo.invoice.ui.project.adapter.AllContactInfoAdapter
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProjectContactFragment : BaseFragment<DetailProjectContacts>(DetailProjectContacts::inflate) {
    companion object {
        const val DATA_PROJECT = "DATA_PROJECT"
        const val DATA_ID = "DATA_ID"
    }

    private val projectViewModel by viewModels<ProjectViewModel>()
    private val id by lazy { arguments?.getLong(DATA_ID) ?: -1 }

    @Inject lateinit var contactInfoAdapter: AllContactInfoAdapter

    @Inject lateinit var projectDao: ProjectDao

    override fun initListeners() {
        binding.backTextView.clicks {
            popBackStack()
        }
        binding.imagePlus.clicks(withAnim = false) {
            if (id != null) {
                val bundle = Bundle()
                val fragment = AddInfoContactProjectFragment()
                fragment.arguments = bundle
                bundle.putLong(DATA_ID, id)
                (requireActivity() as MainActivity).addFragment(fragment)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        projectViewModel.getContactById(id)
        binding.rvShowAllContactInfo.apply {
            projectViewModel.contactList.observe(viewLifecycleOwner) { it ->
                if (it.isEmpty()) {
                    binding.imgContact.isVisible = true
//                    binding.isVisibleContactInfo = false
                    binding.rvShowAllContactInfo.isVisible = false
                } else {
                    binding.imgContact.isVisible = false
                    binding.rvShowAllContactInfo.isVisible = true
                    //   binding.isVisibleContactInfo = true
                    adapter = contactInfoAdapter.apply {
                        data = it.reversed()
                    }
                }

            }
        }
    }

    override fun initData() {
        projectViewModel.getContactById(id)
        binding.rvShowAllContactInfo.apply {
            projectViewModel.contactList.observe(viewLifecycleOwner) { it ->
                if (it.isEmpty()) {
                    binding.imgContact.isVisible = true
//                    binding.isVisibleContactInfo = false
                    binding.rvShowAllContactInfo.isVisible = false
                } else {
                    binding.imgContact.isVisible = false
                    binding.rvShowAllContactInfo.isVisible = true
                 //   binding.isVisibleContactInfo = true
                    adapter = contactInfoAdapter.apply {
                        data = it.reversed()
                    }
                }

            }
        }
        contactInfoAdapter
            .itemClicksInfoContact
            .autoDispose(scope())
            .subscribe { contactInfo ->
                val bundle = Bundle()
                val fragment = ShowDetailInfoContactFragment()
                fragment.arguments = bundle
                bundle.putSerializable(ShowDetailInfoContactFragment.contactInfo, contactInfo)
                addFragment(fragment)
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
                projectViewModel.deleteContactInfo(contactInfoAdapter.data[index])
                contactInfoAdapter.notifyItemRemoved(index)
            }
        }).attachToRecyclerView(binding.rvShowAllContactInfo)
    }

    override fun initView() {
        val nameClient = this.arguments?.getString(DATA_PROJECT)
        binding.nameClientTextView.text = nameClient


    }
}