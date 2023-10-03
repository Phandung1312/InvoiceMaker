package com.bravo.invoice.ui.items.add_item
import androidx.fragment.app.activityViewModels
import com.bravo.basic.view.BaseFragment
import com.bravo.invoice.common.Utils
import com.bravo.invoice.databinding.AddItemClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddItemFragment : BaseFragment<AddItemClass>(AddItemClass::inflate) {
    companion object{
        const val ID_EXTRA = "ID_EXTRA"
    }
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

    override fun initData() {
       val itemId =  arguments?.getInt(ID_EXTRA) ?: -1
        if(itemId != -1){
            binding.tvTitle.text = "Edit Item"
            viewModel.getItem(itemId)
        }
    }
    fun onSave(){
        if(viewModel.isValidateInfo()){
            viewModel.saveItem()
            popBackStack()
        }
    }
    fun onCancel(){
        popBackStack()
    }

    override fun onDestroyView() {
        viewModel.clearData()
        super.onDestroyView()
    }
}