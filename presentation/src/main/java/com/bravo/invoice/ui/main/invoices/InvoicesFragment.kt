package com.bravo.invoice.ui.main.invoices

import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.databinding.InvoicesClass
import com.bravo.invoice.ui.main.invoices.add_invoice.AddInvoiceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvoicesFragment : BaseFragment<InvoicesClass>(InvoicesClass::inflate)
{
    companion object{
        const val PENDING = 1
        const val DONE = 2
    }
    override fun initView() {
        binding.apply {
            fragment = this@InvoicesFragment
        }
    }
    fun onSwitch(option : Int){
        ConstraintSet().apply {
            clone(binding.switchLayout)
            val bias = if(option == PENDING) 0f else 1f
            setHorizontalBias(binding.btnSwitch.id, bias)
            val transition = ChangeBounds()
            transition.duration = 250
            TransitionManager.beginDelayedTransition(binding.switchLayout, transition)
            applyTo(binding.switchLayout)
        }
    }
    fun onAddInvoice(){
        addFragment(AddInvoiceFragment())
    }
}