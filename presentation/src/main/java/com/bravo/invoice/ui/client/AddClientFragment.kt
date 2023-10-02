package com.bravo.invoice.ui.client

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.provider.Settings
import android.text.TextUtils
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
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
    private lateinit var clientObjectData: Client

    companion object {
        const val CONTACT_PERMISSION_CODE = 1
        const val CONTACT_PICK_CODE = 2
        const val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
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
            binding.deleteView.isGone = false
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
        } else {
            binding.addClientTextView.text = "Add client"
            binding.deleteView.isGone = true
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
            if (checkContactPermission()) {
                pickContact()
            } else {
                requestContactPermission()
            }
        }
        binding.cancelTextView.clicks {
            popBackStack()

        }
        binding.deleteView.clicks {
            clientViewModel.deleteClient(clientObjectData)
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
                        popBackStack()
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
                        popBackStack()
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
                    popBackStack()
                } else {
                    clientViewModel.insertClient(clientDataInsert)
                    Toast.makeText(
                        activity,
                        "$billingNameData has been added as a client!",
                        LENGTH_SHORT
                    )
                        .show()
                    popBackStack()
                }
            }

        }
    }


    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(activity)
            .setMessage("Turn on permission on App settings")
            .setPositiveButton("Go to SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", activity?.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun checkContactPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestContactPermission() {
        val permission = arrayOf(Manifest.permission.READ_CONTACTS)
        ActivityCompat.requestPermissions(requireActivity(), permission, CONTACT_PERMISSION_CODE)
    }

    private fun pickContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, CONTACT_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CONTACT_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickContact()
            } else {
               showRotationalDialogForPermission()
            }
        }
    }
    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == CONTACT_PICK_CODE) {
                val cursor1: Cursor
                val cursor2: Cursor?
                val uri = data!!.data
                cursor1 = requireActivity().contentResolver.query(uri!!, null, null, null, null)!!
                if (cursor1.moveToFirst()) {
                    val contactId =
                        cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID))
                    val contactName =
                        cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    binding.billingNameEdt.setText(contactName)
                    val idResults =
                        cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    val idResultHold = idResults.toInt()
                    if (idResultHold == 1) {
                        cursor2 = requireActivity().contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                            null,
                            null
                        )
                        while (cursor2!!.moveToNext()) {
                            val contactNumber =
                                cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            print(contactNumber)
                            binding.mobileEdt.setText(contactNumber.toString())

                        }
                        cursor2.close()
                    }
                    cursor1.close()
                }
            }

        } else {
        }
    }


}