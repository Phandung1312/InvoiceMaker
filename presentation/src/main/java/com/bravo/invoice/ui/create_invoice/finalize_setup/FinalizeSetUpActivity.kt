package com.bravo.invoice.ui.create_invoice.finalize_setup

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.R
import com.bravo.invoice.databinding.ActivityFinalizeSetUpBinding

class FinalizeSetUpActivity : BaseActivity<ActivityFinalizeSetUpBinding>(ActivityFinalizeSetUpBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalize_set_up)
    }

}