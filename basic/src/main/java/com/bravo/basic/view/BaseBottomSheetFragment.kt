package com.bravo.basic.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseBottomSheetFragment<VB : ViewDataBinding>   (private val inflate: Inflate<VB>) : BottomSheetDialogFragment() {
    private var _binding: VB? = null
    val binding: VB
        get() = _binding
            ?: throw RuntimeException("Should only use binding after onCreateView and before onDestroyView")
    protected val activity by lazy { requireActivity() as? AppCompatActivity }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)

        initView()
        initListeners()
        initData()
        return binding.root
    }
    open fun initView(){

    }

    open fun  initListeners(){

    }

    open fun initData(){

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}