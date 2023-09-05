package com.bravo.basic.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.bravo.basic.extensions.transparent

abstract class BaseActivity<VB : ViewBinding>(val bindingInflater: (LayoutInflater) -> VB) : AppCompatActivity() {
    val binding: VB by lazy { bindingInflater(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparent(isLightStatusBar = true, isLightNavigationBar = true)
        setContentView(binding.root)
        initView()
        initListener()
        initData()
    }

    open  fun initView() {

    }

    open  fun initListener() {

    }

    open fun initData() {

    }
}