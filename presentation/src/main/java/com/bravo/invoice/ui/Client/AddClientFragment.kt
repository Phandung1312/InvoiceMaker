package com.bravo.invoice.ui.Client


import android.text.TextUtils
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.domain.model.Client
import com.bravo.invoice.databinding.AddClientClass
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddClientFragment : BaseFragment<AddClientClass>(AddClientClass::inflate) {
    @Inject
    lateinit var clientViewModel: ClientViewModel
    override fun initData() {
    }

    override fun initListeners() {
        binding.saveTextView.clicks(withAnim = false) {
            insertClient()
        }
    }


    private fun insertClient() {
        val billingNameData = binding.billingNameEdt.text.toString()
        val mobileData = binding.mobileEdt.text.toString()
        val emailData = binding.emailEdt.text.toString()
        val contactData = binding.contactEdt.text.toString()
        val phoneContactData = binding.phoneEdt.text.toString()
        val urlWebsiteData = binding.websiteEdt.text.toString()
        val taxNumberData = binding.taxEdt.text.toString()
        val billingAddressData = binding.billingAddressEdt.text.toString()
        val paymentData = binding.noteEdt.text.toString()
        val noteData = binding.noteEdt.text.toString()
        if (!TextUtils.isEmpty(billingNameData)) {
            val clientDataInsert = Client(
                0,
                billingNameData,
                mobileData,
                emailData,
                contactData,
                phoneContactData,
                urlWebsiteData,
                taxNumberData,
                paymentData,
                billingAddressData,
                noteData
            )
            clientViewModel.insert(clientDataInsert)
            Toast.makeText(activity, "Add data successfully!", LENGTH_SHORT).show()
        }
    }
}