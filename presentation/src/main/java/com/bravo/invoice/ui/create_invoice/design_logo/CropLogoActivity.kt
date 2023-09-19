package com.bravo.invoice.ui.create_invoice.design_logo


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.bravo.basic.extensions.makeToast
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.R
import com.bravo.invoice.common.AppPool
import com.bravo.invoice.databinding.ActivityCropLogoBinding
import com.canhub.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CropLogoActivity : BaseActivity<ActivityCropLogoBinding>(ActivityCropLogoBinding::inflate) {
    @Inject lateinit var appPool: AppPool
    override fun initView() {
        binding.activity = this@CropLogoActivity
        val bitmap = if(appPool.currentOption == DesignLogoFragment.LOGO) appPool.logo else appPool.additionalImage
        binding.cropImageView.setImageBitmap(bitmap)
    }
    fun onNext(){
        if(appPool.currentOption == DesignLogoFragment.LOGO){
            appPool.logo = binding.cropImageView.getCroppedImage()
        }
        else{
            appPool.additionalImage = binding.cropImageView.getCroppedImage()
        }
        val intent = Intent(this, AdjustLogoActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun onClose(){
        finish()
    }
}
