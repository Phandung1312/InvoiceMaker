package com.bravo.basic.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
abstract class BaseFragment<VB : ViewDataBinding>   (private val inflate: Inflate<VB>) : Fragment() {
    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

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

    fun addFragment(fragment : Fragment){
        (requireActivity() as BaseActivity<*>).addFragment(fragment)
    }

    fun popBackStack(isVisibleNavBar : Boolean = false){
        (requireActivity() as BaseActivity<*>).popBackStack(isVisibleNavBar)
    }
    fun popBackStack(fragment : String){
        (requireActivity() as BaseActivity<*>).popBackStack(fragment)
    }
    fun translateFragment(fragment: Fragment){
        (requireActivity() as BaseActivity<*>).translateFragment(fragment)
    }

    fun visibleBottomLayout(isVisible : Boolean){
        (requireActivity() as BaseActivity<*>).visibleBottomLayout(isVisible)
    }

    fun showConfirmDialog(
         title  : String,
        content : String,
         positiveText : String,
         negativeText : String,
         positiveCallback : () -> Unit,
         negativeCallback : (() -> Unit)?
    ){
        (requireActivity() as BaseActivity<*>).showConfirmDialog(
            title  ,
            content ,
            positiveText ,
            negativeText ,
            positiveCallback ,
            negativeCallback
        )
    }
    fun showConfirmDialog(
        title  : Int,
        content : Int,
        positiveText : Int,
        negativeText : Int,
        positiveCallback : () -> Unit,
        negativeCallback : () ->  (() -> Unit)?
    ){

    }
    fun showInfoDialog(
        title: String, content: String, confirmText: String
    ){
        (requireActivity() as BaseActivity<*>).showInfoDialog(
            title  ,
            content ,
            confirmText
        )
    }
    fun showInfoDialog(
        title: Int, content: Int, confirmText: Int
    ){
        (requireActivity() as BaseActivity<*>).showInfoDialog(
            title  ,
            content ,
            confirmText
        )
    }

}