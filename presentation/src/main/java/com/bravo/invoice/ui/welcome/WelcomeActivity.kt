package com.bravo.invoice.ui.welcome

import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.databinding.ActivityWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(ActivityWelcomeBinding::inflate) {

    override fun initView() {
        binding.welcomeActivity = this
    }

    fun getStart(){

    }
}