package com.bravo.invoice.ui.create_invoice.design_logo


import android.content.Intent
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.common.pool.InvoicePool
import com.bravo.invoice.databinding.ActivityCropLogoBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CropLogoActivity : BaseActivity<ActivityCropLogoBinding>(ActivityCropLogoBinding::inflate) {
    @Inject lateinit var invoicePool: InvoicePool
    override fun initView() {
        binding.activity = this@CropLogoActivity
        val bitmap = if(invoicePool.currentOption == DesignLogoFragment.LOGO) invoicePool.logo else invoicePool.additionalImage
        binding.cropImageView.setImageBitmap(bitmap)
    }
    fun onNext(){
        if(invoicePool.currentOption == DesignLogoFragment.LOGO){
            invoicePool.logo = binding.cropImageView.getCroppedImage()
        }
        else{
            invoicePool.additionalImage = binding.cropImageView.getCroppedImage()
        }
        val intent = Intent(this, AdjustLogoActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun onClose(){
        finish()
    }
}
