package com.bravo.invoice.ui.create_invoice.design_logo


import android.content.Intent
import com.bravo.basic.extensions.clicks
import com.bravo.basic.extensions.setDrawableString
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.adapter.DesignLogoAdapter
import com.bravo.invoice.databinding.ActivitySelectLogoBinding
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectLogoActivity : BaseActivity<ActivitySelectLogoBinding>(ActivitySelectLogoBinding::inflate) {
    @Inject lateinit var designLogoAdapter: DesignLogoAdapter

    override fun initView() {
        binding.activtity = this@SelectLogoActivity
        binding.rvTemplateLogos.apply {
            var listItem : ArrayList<String> = arrayListOf()
            for(i in 1.. 100){
                listItem.add("design_logo_$i")
            }
            adapter = designLogoAdapter.apply {
                data = listItem
            }
        }
    }

    override fun initListener() {
        designLogoAdapter.itemClicks.autoDispose(scope()).subscribe {
            binding.ivLogo.setDrawableString(it)
        }
        binding.ivClose.clicks(withAnim = false) {
            finish()
        }
        binding.tvConfirm.clicks {
            val intent = Intent(this, CropLogoActivity::class.java)
            intent.putExtra(CropLogoActivity.LOGO_STRING_EXTRA, designLogoAdapter.data[designLogoAdapter.selectedItem])
            startActivity(intent)
        }
    }
}