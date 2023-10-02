package com.bravo.invoice.ui.project

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.bravo.basic.extensions.clicks
import com.bravo.basic.extensions.makeToast
import com.bravo.invoice.R
import com.bravo.invoice.databinding.BottomSheetPhotoBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.IOException

@AndroidEntryPoint
class BottomSheetAddPhoto : BottomSheetDialogFragment() {


    private val projectViewModel by activityViewModels<ProjectViewModel>()
    private var idReceived: Long = -1
    private var imgList: ArrayList<String> = arrayListOf()
    private lateinit var binding: BottomSheetPhotoBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let {
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetPhotoBinding.inflate(inflater, container, false)
        initData()
        initListener()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        pickImageFromGalleryForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val uri = result.data?.data
                    if (uri != null) {
                        val uriImg = uri.toString()
                        imgList.add(uriImg)
                        projectViewModel.updateFile(idReceived, imgList)
                        dismiss()
                    }
                }
            }

        takePhotoForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val image = result.data?.extras?.get("data") as Bitmap
                    val uri = try {
                        val path = MediaStore.Images.Media.insertImage(
                            requireContext().contentResolver,
                            image,
                            "Title",
                            null
                        )
                        val data = Uri.parse(path)
                        imgList.add(data.toString())
                        projectViewModel.updateFile(idReceived, imgList)
                        dismiss()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        null
                    }
                    dismiss()
                }
            }
    }

    private fun initData() {}

    private fun initListener() {
        binding.takePhotoView.clicks(withAnim = false) {
            cameraCheckPermission()
        }
        binding.selectImageView.clicks(withAnim = false) {
            openGallery()
        }
        binding.cancelView.clicks(withAnim = false) {
            dismiss()
        }
    }

    fun receivedId(id: Long, files: List<String>) {
        idReceived = id
        imgList = files as ArrayList<String>
    }


    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoForResult!!.launch(intent)
    }

    private fun cameraCheckPermission() {
        Dexter.withContext(requireActivity())
            .withPermissions(
                if (Build.VERSION.SDK_INT > 32) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                        p0?.let {
                            if (p0.areAllPermissionsGranted()) {
                                openCamera()
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        showRotationalDialogForPermission()
                    }

                }
            ).onSameThread().check()
    }

    private var pickImageFromGalleryForResult: ActivityResultLauncher<Intent>? = null
    private var takePhotoForResult: ActivityResultLauncher<Intent>? = null
    private fun openGallery() {
        Dexter.withContext(requireActivity()).withPermission(
            if (Build.VERSION.SDK_INT > 32) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                pickImageFromGalleryForResult?.launch(intent)
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                showRotationalDialogForPermission()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                showRotationalDialogForPermission()
            }

        }).onSameThread().check()
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

}