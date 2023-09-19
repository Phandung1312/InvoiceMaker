package com.bravo.invoice.ui.client

import android.app.Activity
import android.content.Intent
import android.provider.ContactsContract
import android.text.TextUtils
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.domain.model.Client
import com.bravo.invoice.R
import com.bravo.invoice.databinding.AddClientClass
import com.bravo.invoice.ui.main.MainActivity
import com.bravo.invoice.ui.setupinfo.EnterAddressBottomSheet
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


@AndroidEntryPoint
class AddClientFragment : BaseFragment<AddClientClass>(AddClientClass::inflate) {

    private val clientViewModel by viewModels<ClientViewModel>()
    private val subjectEmailChanges: Subject<String> = PublishSubject.create()
    private  val subjectPhoneChanges : Subject<String> = PublishSubject.create()
    var PATTERN: Pattern = Pattern.compile(REG)
    fun CharSequence.isPhoneNumber(): Boolean = PATTERN.matcher(this).find()
    private lateinit var clientObjectData: Client

    companion object {
        private const val SELECT_PHONE_NUMBER = 111
        const val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        const val REG = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}\$"
    }

    override fun initView() {
        binding.fragment = this
        binding.viewModel = clientViewModel
        intObservable()
    }

    private fun intObservable() {
        subjectEmailChanges
            .debounce(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .autoDispose(scope())
            .subscribe { email ->
                binding.textEmptyValidEmail.isVisible = when {
                    email.isNullOrEmpty() -> false
                    else -> !(email.matches(Regex(emailPattern)) && email.isNotEmpty())
                }
            }
    }

    override fun initData() {
        val receivedDataClient =
            arguments?.getSerializable(DetailsClientFragment.CLIENT_EXTRA) as? Client
        binding.emailEdt.addTextChangedListener {
            subjectEmailChanges.onNext(
                it?.trim()?.toString() ?: ""
            )
        }
        if (receivedDataClient != null) {
            clientObjectData = receivedDataClient
            binding.billingNameEdt.setText(receivedDataClient.billingName)
            binding.addClientTextView.text = "Edit Client"
            binding.mobileEdt.setText(receivedDataClient.mobileNumber)
            binding.emailEdt.setText(receivedDataClient.email)
            binding.billingAddressEdt.setText(receivedDataClient.address)
            binding.contactEdt.setText(receivedDataClient.nameContact)
            binding.phoneEdt.setText(receivedDataClient.phoneContact)
            binding.websiteEdt.setText(receivedDataClient.website)
            binding.taxEdt.setText(receivedDataClient.taxNum)
            binding.noneText.text = receivedDataClient.payment
            binding.noteEdt.setText(receivedDataClient.note)
            binding.viewChooseContacts.isGone = true
        }
    }

    override fun initListeners() {
        binding.saveTextView.clicks(withAnim = false) {
            val billingNameData = binding.billingNameEdt.text.toString()
            binding.emptyImg.isVisible = billingNameData.isEmpty()
            if (billingNameData.isEmpty()) {
                Toast.makeText(activity, "This field can't be empty", Toast.LENGTH_SHORT).show()
                binding.saveTextView.isVisible = true
            } else {
                insertOrUpdateClient()
            }

        }
        binding.viewBillingAddress.clicks(withAnim = false) {
            openAddBillingAddress()
        }
        binding.viewChooseContacts.clicks {
            val contactPickerIntent =
                Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            activity?.startActivityForResult(contactPickerIntent, 1)
        }
        binding.cancelTextView.clicks {
            (requireActivity() as MainActivity).backFragment()
        }
    }

    private fun openAddBillingAddress() {
        val bottomSheet =
            EnterAddressBottomSheet(binding.billingAddressEdt.text.toString()) { address ->
                binding.billingAddressEdt.isVisible = true
                binding.billingAddressEdt.setText(address)
            }
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun insertOrUpdateClient() {
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
            if (emailData.isNotEmpty()) {
                if (emailData.matches(Regex(emailPattern))) {
                    binding.textEmptyValidEmail.isVisible = true
                    if (binding.viewChooseContacts.isGone) {
                        val clientDataInsert = Client(
                            clientObjectData.id,
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
                        clientViewModel.updateClient(clientDataInsert)
                    } else {
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
                        clientViewModel.insertClient(clientDataInsert)
                        Toast.makeText(
                            activity,
                            "$billingNameData has been added as a client!",
                            LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    binding.textEmptyValidEmail.isVisible = false
                }

            } else {
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
                if (binding.viewChooseContacts.isGone) {
                    val clientDataInsert = Client(
                        clientObjectData.id,
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
                    clientViewModel.updateClient(clientDataInsert)
                } else {
                    clientViewModel.insertClient(clientDataInsert)
                    Toast.makeText(
                        activity,
                        "$billingNameData has been added as a client!",
                        LENGTH_SHORT
                    )
                        .show()
                }
            }

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHONE_NUMBER && resultCode == Activity.RESULT_OK) {
            val contactUri = data?.data ?: return
            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )
            val cursor = requireContext().contentResolver.query(
                contactUri, projection,
                null, null, null
            )

            if (cursor != null && cursor.moveToFirst()) {
                val nameIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val numberIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val name = cursor.getString(nameIndex)
                val number = cursor.getString(numberIndex)
            }
            cursor?.close()
        }
    }
}