package com.bravo.invoice.ui.create_invoice.design_logo


import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.databinding.ActivityCropLogoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CropLogoActivity : BaseActivity<ActivityCropLogoBinding>(ActivityCropLogoBinding::inflate) {
    companion object {
        const val LOGO_STRING_EXTRA ="LOGO_STRING_EXTRA"
    }
    override fun initView() {

    }
}