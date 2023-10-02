package com.bravo.invoice.ui.ClientPayment


import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.TermsClass

import com.bravo.invoice.models.Terms
import com.bravo.invoice.ui.ClientPayment.adapter.AdapterTerms
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TermsFragment : BaseFragment<TermsClass>(TermsClass::inflate) {
    val items = mutableListOf<Terms>()


    @Inject
    lateinit var termsAdapter: AdapterTerms
    override fun initListeners() {
        binding.backTextView.clicks(withAnim = false) {
            popBackStack()
        }
    }

    override fun initView() {

    }

    override fun initData() {
        setData()
        binding.rvAllTerms.apply {
            adapter = termsAdapter.apply {
                data = items
            }
        }
    }

    private fun setData() {
        items.add(Terms("Same day"))
        items.add(Terms("7 days"))
        items.add(Terms("14 days"))
        items.add(Terms("21 days"))
        items.add(Terms("30 days"))
        items.add(Terms("45 days"))
        items.add(Terms("60 days"))
        items.add(Terms("90 days"))

    }
}