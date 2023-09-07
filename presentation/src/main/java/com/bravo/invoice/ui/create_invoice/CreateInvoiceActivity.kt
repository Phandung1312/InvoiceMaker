package com.bravo.invoice.ui.create_invoice


import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.R
import com.bravo.invoice.databinding.ActivityCreateInvoiceBinding

class CreateInvoiceActivity : BaseActivity<ActivityCreateInvoiceBinding>(ActivityCreateInvoiceBinding::inflate) {

    override fun initView() {
        setNavController()
    }
    private fun setNavController(){
        try{

            binding.bottomNavigation.setOnItemSelectedListener { item ->
                val selectedFragment = when (item.itemId) {
                    R.id.template -> 1
                    R.id.logo -> 2
                    R.id.color -> 3
                    R.id.banner -> 4
                    R.id.watermark -> 5
                    else -> null
                }
                true
            }
        }catch(e : Exception){

        }
    }
}