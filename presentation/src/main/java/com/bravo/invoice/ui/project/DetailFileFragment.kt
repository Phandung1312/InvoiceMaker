package com.bravo.invoice.ui.project

import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.ScreenDetailFile

class DetailFileFragment : BaseFragment<ScreenDetailFile>(ScreenDetailFile::inflate) {
    companion object{
        const val DATA_INFO = "DATA_INFO"
    }
    override fun initListeners() {
        binding.imgDelete.clicks(withAnim = false) {

        }
        binding.imgClose.clicks(withAnim = false) {

        }
    }

    override fun initView() {

    }
}