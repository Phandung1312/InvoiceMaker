package com.bravo.invoice.ui.create_invoice.design_logo


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.R
import com.bravo.invoice.databinding.ActivityCropLogoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CropLogoActivity : BaseActivity<ActivityCropLogoBinding>(ActivityCropLogoBinding::inflate) {
    companion object {
        const val LOGO_STRING_EXTRA ="LOGO_STRING_EXTRA"
    }
    override fun initView() {
        binding.activity = this@CropLogoActivity
        binding.cropImageView.setImageBitmap(getDrawable(R.drawable.design_logo_1)?.let {
            drawableToBitmap(
                it
            )
        })
    }
    fun onNext(){
        val intent = Intent(this, AdjustLogoActivity::class.java)
        startActivity(intent)
    }
    fun onTurn(){
        binding.cropImageView.rotateImage(90)
    }
    fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.draw(canvas)
        return bitmap
    }
}

//object ImageConverter {
//
//
//    fun drawableToBitmap(context: Context, drawableId: Int): Bitmap {
//        val options = BitmapFactory.Options()
//        options.inScaled = false
//        return BitmapFactory.decodeResource(context.resources, drawableId, options)
//    }
//}