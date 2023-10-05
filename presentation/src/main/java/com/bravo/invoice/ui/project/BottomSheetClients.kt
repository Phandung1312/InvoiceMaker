package com.bravo.invoice.ui.project

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bravo.basic.extensions.clicks
import com.bravo.domain.model.Client
import com.bravo.invoice.R
import com.bravo.invoice.adapter.ClientAdapter
import com.bravo.invoice.databinding.BottomSheetAllclientsBinding
import com.bravo.invoice.ui.client.AddClientFragment
import com.bravo.invoice.ui.client.ClientViewModel
import com.bravo.invoice.ui.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetClients(
    private val result: (Client) -> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAllclientsBinding
    private val clientViewModel by viewModels<ClientViewModel>()
    @Inject
    lateinit var clientAdapter: ClientAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let {
                val behaviour = BottomSheetBehavior.from(parentLayout)
                setupFullHeight(parentLayout)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.skipCollapsed = true
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetAllclientsBinding.inflate(inflater, container, false)
        initData()
        initListener()
        return binding.root
    }

    private fun initData() {
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
                showAlertConfirm(
                    clientAdapter.data[index].billingName!!,
                    clientAdapter.data[index],
                    index
                )
            }
        }).attachToRecyclerView(binding.rVShowAllDataClient)
        handleSearchClient()
    }

    private fun showAlertConfirm(titleData: String, client: Client, index: Int) {
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

    private fun sendDataToParent(data: Client) {
        result.invoke(data)
        dismiss()
    }

    private fun initListener() {
        clientAdapter
            .itemClicks
            .autoDispose(scope())
            .subscribe { client ->
                sendDataToParent(client)
            }
        binding.cancelTextView.clicks {
            dismiss()
        }
        binding.addClientBtn.clicks(withAnim = false) {
            //addFragment(AddClientFragment())
            val createAddFragment = AddClientFragment()
            val ft = childFragmentManager.beginTransaction()
            ft.replace(R.id.bottom_sheet_client, createAddFragment)
            ft.commit()
        }
    }

    private fun handleSearchClient() {
        binding.edtSearchDialog.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.rVShowAllDataClient.apply {
                    clientViewModel.searchClient(binding.edtSearchDialog.text.toString())
                        .observe(viewLifecycleOwner) { it ->
                            if (it.isEmpty()) {
                                binding.noResultTextview.isGone = false
                                binding.rVShowAllDataClient.isVisible = false
                            } else {
                                binding.rVShowAllDataClient.isVisible = true
                                binding.noResultTextview.isGone = true
                                adapter = clientAdapter.apply {
                                    data = it.reversed()
                                }
                            }
                        }

                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }


}