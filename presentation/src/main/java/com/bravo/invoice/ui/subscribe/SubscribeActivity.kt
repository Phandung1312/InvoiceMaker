package com.bravo.invoice.ui.subscribe


import android.content.Intent
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.databinding.ActivitySubsribeBinding
import com.bravo.invoice.ui.setupinfo.SetUpInfoActivity

class SubscribeActivity : BaseActivity<ActivitySubsribeBinding>(ActivitySubsribeBinding::inflate) {
    override fun initView() {
        binding.subscribeActivity = this
    }
    fun onSubscribe(){
        val intent = Intent(this, SetUpInfoActivity::class.java)
        startActivity(intent)
    }
}