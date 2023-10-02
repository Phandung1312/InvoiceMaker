package com.bravo.invoice.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bravo.basic.extensions.clicks
import com.bravo.invoice.R
import com.bravo.invoice.databinding.DialogConfirmBinding

class ConfirmDialog(
    private val title  : String,
    private val content : String,
    private val positiveText : String,
    private val negativeText : String,
    private val positiveCallback : () -> Unit,
    private val negativeCallback : (() -> Unit)?
) : DialogFragment() {
    private lateinit var binding : DialogConfirmBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogConfirmBinding.inflate( inflater, container, false)
        initView()
        initListener()
        isCancelable = false
        return binding.root
    }

    private fun initListener() {
        binding.apply {
            tvPositive.clicks(withAnim = false) {
                positiveCallback.invoke()
                dismiss()
            }

            tvNegative.clicks(withAnim = false) {
                negativeCallback?.invoke()
                dismiss()
            }
        }

    }

    private fun initView() {
        binding.apply {
            tvTitle.text = title
            tvContent.text = content
            tvPositive.text = positiveText
            tvNegative.text = negativeText
        }
    }

    override fun onStart() {
        super.onStart()
        val layoutParams = dialog?.window?.attributes
        layoutParams?.width = (resources.displayMetrics.widthPixels * 0.8).toInt()
        dialog?.window?.attributes = layoutParams
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog?.window?.setGravity(android.view.Gravity.CENTER)
    }
}