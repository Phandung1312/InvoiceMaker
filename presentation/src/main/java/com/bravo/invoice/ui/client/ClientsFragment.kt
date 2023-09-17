package com.bravo.invoice.ui.client

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.domain.model.Client
import com.bravo.invoice.R
import com.bravo.invoice.adapter.ClientAdapter
import com.bravo.invoice.common.ConfigApp
import com.bravo.invoice.databinding.ClientsView
import com.bravo.invoice.ui.main.MainActivity
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ClientsFragment : BaseFragment<ClientsView>(ClientsView::inflate) {
    private val clientViewModel by viewModels<ClientViewModel>()

    @Inject
    lateinit var clientAdapter: ClientAdapter


    override fun initView() {
        binding.clientFragment = this
    }

    override fun initData() {
        binding.rVShowAllDataClient.apply {
            clientViewModel.getAllClient.observe(viewLifecycleOwner) { it ->
                if (it.isEmpty()) {
                    binding.viewCenter.isVisible = true
                    binding.rVShowAllDataClient.isVisible = false
                } else {
                    binding.rVShowAllDataClient.isVisible = true
                    binding.viewCenter.isVisible = false
                    adapter = clientAdapter.apply {
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
                 showAlertConfirm(clientAdapter.data[index].billingName!!, clientAdapter.data[index], index)
            }
        }).attachToRecyclerView(binding.rVShowAllDataClient)
    }

    override fun initListeners() {
        binding.addClientBtn.clicks {
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.viewFragment, AddClientFragment())
                ?.commit()
        }

        clientAdapter
            .itemClicks
            .autoDispose(scope())
            .subscribe {client ->
                val bundle = Bundle()
                val fragment = DetailsClientFragment()
                fragment.arguments = bundle
                (requireActivity() as MainActivity).addFragment(fragment)
                bundle.putSerializable(DetailsClientFragment.CLIENT_EXTRA,client)

            }


    }

    private fun showAlertConfirm(titleData:String, client:Client, index: Int) {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setTitle("Delete $titleData?")
        alertDialogBuilder.setMessage("Deleting won't affect invoices or other documents that include these clients.")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            clientViewModel.deleteClient(client)
            clientAdapter.notifyItemRemoved(index)
        }
        alertDialogBuilder.setNegativeButton("No") { _, _ ->
            clientAdapter.notifyItemChanged(index)
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


}