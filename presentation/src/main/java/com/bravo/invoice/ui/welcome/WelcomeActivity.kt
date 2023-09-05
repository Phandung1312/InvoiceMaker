package com.bravo.invoice.ui.welcome

import android.content.Intent
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.databinding.ActivityWelcomeBinding
import com.bravo.invoice.ui.setupinfo.SetUpInfoActivity
import com.bravo.invoice.ui.subscribe.SubscribeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(ActivityWelcomeBinding::inflate) {

    override fun initView() {
        binding.welcomeActivity = this
    }

    fun getStart(){
        val intent = Intent(this, SubscribeActivity::class.java)
        startActivity(intent)
    }
}