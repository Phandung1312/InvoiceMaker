package com.bravo.invoice.ui.items.add_item

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.common.Utils
import com.bravo.invoice.databinding.AddItemClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddItemFragment : BaseFragment<AddItemClass>(AddItemClass::inflate) {
    private val viewModel by activityViewModels<AddItemViewModel>()
    override fun initView() {
        binding.fragment = this@AddItemFragment
        binding.viewModel = viewModel
    }
    fun onShowUnitType(){
        UnitTypeDialog().show(parentFragmentManager, null)
    }

    override fun initListeners() {
        binding.edRate.apply {
            setOnFocusChangeListener { _, hasFocus  ->
                if(hasFocus){
                        val text = if(viewModel.item.rate != 0L) viewModel.item.rate.toString() else ""
                        setText(text)
                        setSelection(text.length)
                }
                else{
                    val text = "đ" + Utils.formatCurrency(viewModel.item.rate.toDouble())
                    setText(text)
                }
            }
        }
        binding.edCost.apply {
            setOnFocusChangeListener { _, hasFocus  ->
                if(hasFocus){
                    val text = if(viewModel.item.cost != 0L) viewModel.item.cost.toString() else ""
                    setText(text)
                    setSelection(text.length)
                }
                else{
                    val text = "đ" + Utils.formatCurrency(viewModel.item.cost.toDouble())
                    setText(text)
                }
            }
        }
    }
    fun onSave(){
        if(viewModel.isValidateInfo()){
            viewModel.saveItem()
        }
    }
}