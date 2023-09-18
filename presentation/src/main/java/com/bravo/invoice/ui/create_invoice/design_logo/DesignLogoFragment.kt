package com.bravo.invoice.ui.create_invoice.design_logo

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.bravo.basic.extensions.makeToast
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.R
import com.bravo.invoice.common.AppPool
import com.bravo.invoice.databinding.DesignLogoClass
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class DesignLogoFragment : BaseFragment<DesignLogoClass>(DesignLogoClass::inflate) {
    @Inject lateinit var appPool: AppPool
    override fun initView() {
        binding.fragment = this
    }

    fun onAddLogo(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setItems(arrayOf("Take a photo", "Chose from photos")){ _,which ->
            when(which){
                0 -> {
                    cameraCheckPermission()
                }
                1 ->{
                    openGallery()
                }
            }
        }
        builder.setNegativeButton("Cancel"
        ) { _, _ -> }
        val alterDialog = builder.create()
        alterDialog.setOnShowListener{
            alterDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(requireContext().getColor(R.color.blue_button))
        }
        alterDialog.show()
    }
    private fun cameraCheckPermission(){
        Dexter.withContext(activity)
            .withPermissions(if(Build.VERSION.SDK_INT > 32 ) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA).withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                        p0?.let {
                            if(p0.areAllPermissionsGranted()){
                                activity?.makeToast("OK")
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
    private fun openCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoForResult.launch(intent)
    }
    private fun showRotationalDialogForPermission(){
        AlertDialog.Builder(activity)
            .setMessage("Turn on permission on App settings")
            .setPositiveButton("Go to SETTINGS"){_,_->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package",activity?.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }
                catch (e : ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CANCEL"){dialog,_->
                dialog.dismiss()
            }.show()
    }
    private fun openGallery(){
        Dexter.withContext(activity).withPermission(
            if(Build.VERSION.SDK_INT > 32 ) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                pickImageFromGalleryForResult.launch(intent)
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                activity?.makeToast("You have denied the storage permission select image")
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
    private val takePhotoForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            goToCropLogo(imageBitmap)
        }
    }
    private val pickImageFromGalleryForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            val bitmap: Bitmap?

            if (uri != null) {
                try {
                    val inputStream = requireContext().contentResolver.openInputStream(uri)
                    bitmap = BitmapFactory.decodeStream(inputStream)
                    bitmap?.let {
                        goToCropLogo(it)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
    fun onDesignLogo(){
        val intent = Intent(requireActivity(), SelectLogoActivity::class.java)
        startActivity(intent)
    }
    private fun goToCropLogo(bitmap : Bitmap){
        appPool.logo = bitmap
        val intent = Intent(requireActivity(), CropLogoActivity::class.java)
        startActivity(intent)
    }
}