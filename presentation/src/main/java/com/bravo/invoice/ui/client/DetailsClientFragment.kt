package com.bravo.invoice.ui.client


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.domain.model.Client
import com.bravo.invoice.R
import com.bravo.invoice.databinding.DetailsViewClass
import com.bravo.invoice.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsClientFragment : BaseFragment<DetailsViewClass>(DetailsViewClass::inflate) {
    companion object {
        const val CLIENT_EXTRA = "CLIENT_EXTRA"
    }

    private lateinit var clientData: Client

    override fun initData() {
        val receivedData = arguments?.getSerializable(CLIENT_EXTRA) as? Client
        if (receivedData != null) {
            clientData = receivedData
            val nameBilling = receivedData.billingName
            val mobileNumber = receivedData.mobileNumber
            val email = receivedData.email
            val contactName = receivedData.nameContact
            val websiteData = receivedData.website
            binding.nameBillingNameTextView.text = nameBilling
            binding.mobileText.text = mobileNumber
            binding.emailText.text = email
            binding.nameContactText.text = contactName
            binding.nameWebText.text = websiteData
            if (mobileNumber!!.isNotEmpty() && email!!.isEmpty()) {
                binding.statusEmailImg.setImageResource(R.drawable.ic_gmail)
                binding.statusEmailTextView.text = "Add email"
            }
            if (mobileNumber.isEmpty() && email!!.isNotEmpty()) {
                binding.statusPhoneTextview.text = "Add number"
                binding.viewMess.isGone = true
                binding.viewGroupChoice2.weightSum = 3F
                val params1 = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params1.weight = 1f
                binding.viewCall.layoutParams = params1
                binding.viewMail.layoutParams = params1
                binding.viewCreate2.layoutParams = params1
                binding.imgStatusPhone.setImageResource(R.drawable.ic_phone)
            }
            if (mobileNumber.isEmpty() && email!!.isEmpty()) {
                binding.viewMess.isGone = true
                binding.statusPhoneTextview.text = "Add number"
                binding.statusEmailTextView.text = "Add email"
                binding.statusEmailImg.setImageResource(R.drawable.ic_gmail)
                binding.imgStatusPhone.setImageResource(R.drawable.ic_phone)

                binding.viewGroupChoice2.weightSum = 3F
                val params1 = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params1.weight = 1f
                binding.viewCall.layoutParams = params1
                binding.viewMail.layoutParams = params1
                binding.viewCreate2.layoutParams = params1
            }
            if (mobileNumber.isEmpty() && email!!.isEmpty() && contactName!!.isEmpty() && websiteData!!.isEmpty()) {
                binding.textShowNoInfo.isVisible = true
            }
            binding.viewInfoMobile.isGone = mobileNumber.isEmpty()
            binding.viewInfoEmail.isGone = email!!.isEmpty()
            binding.viewInfoContact.isGone = contactName!!.isEmpty()
            binding.viewInfoWeb.isGone = websiteData!!.isEmpty()
        }
    }

    override fun initListeners() {
        binding.viewInfo.clicks {
            binding.isVisible = true
        }
        binding.viewActivity.clicks {
            binding.isVisible = false
        }
        binding.backTextView.clicks {
            val fragmentManager = requireActivity().
            fragmentManager.popBackStack()
        }
        binding.viewCall.clicks {
            if (clientData.mobileNumber!!.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${clientData.mobileNumber}")
                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(intent)
                } else {
                }
            } else {
                val bundle = Bundle()
                val fragment = AddClientFragment()
                fragment.arguments = bundle
                (requireActivity() as MainActivity).addFragment(fragment)
                bundle.putSerializable(CLIENT_EXTRA, clientData)
            }
        }
        binding.viewMail.clicks {
            if (clientData.email!!.isNotEmpty()) {
                val recipientEmail = clientData.email
                val subject = ""
                val message = ""
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "message/rfc822"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                emailIntent.putExtra(Intent.EXTRA_TEXT, message)
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send Email"))
                } catch (ex: android.content.ActivityNotFoundException) {
                }
            } else {
                val bundle = Bundle()
                val fragment = AddClientFragment()
                fragment.arguments = bundle
                (requireActivity() as MainActivity).addFragment(fragment)
                bundle.putSerializable(CLIENT_EXTRA, clientData)
            }
        }
        binding.viewMess.clicks {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.putExtra("address", clientData.mobileNumber)
            intent.type = "vnd.android-dir/mms-sms"
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {

            }
        }


    }


}