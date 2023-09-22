package com.bravo.basic.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.bravo.basic.extensions.transparent

abstract class BaseActivity<VB : ViewBinding>(val bindingInflater: (LayoutInflater) -> VB) : AppCompatActivity() {
    val binding: VB by lazy { bindingInflater(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initListener()
        initObservable()
        initData()
    }

    open  fun initView() {

    }

    open  fun initListener() {

    }

    open fun initData() {

    }
    open fun initObservable(){

    }
    open fun addFragment(fragment : Fragment){

    }

    open fun popBackStack(){

    }

    open fun translateFragment(fragment : Fragment){

    }
}