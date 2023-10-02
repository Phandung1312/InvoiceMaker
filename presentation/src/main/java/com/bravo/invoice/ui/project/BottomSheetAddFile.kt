package com.bravo.invoice.ui.project

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bravo.basic.extensions.clicks
import com.bravo.invoice.R
import com.bravo.invoice.databinding.BottomSheetAddfilesProjectBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetAddFile : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAddfilesProjectBinding
    private val bottomSheetAddPhoto by lazy {
        BottomSheetAddPhoto()
    }
    private var idProject: Long = -1
    private var listProject: ArrayList<String> = arrayListOf()
    private lateinit var pdfUri: Uri
    private val projectViewModel by activityViewModels<ProjectViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let {
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetAddfilesProjectBinding.inflate(inflater, container, false)
        initData()
        initListener()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    private fun initData() {}
    fun setData(dataId: Long, listImg: List<String>) {
        idProject = dataId
        listProject = listImg as ArrayList<String>
    }

    private fun initListener() {
        binding.cancelView.clicks(withAnim = false) {
            dismiss()
        }
        binding.photosView.clicks(withAnim = false) {
            bottomSheetAddPhoto.receivedId(idProject, listProject)
            bottomSheetAddPhoto.show(parentFragmentManager, bottomSheetAddPhoto.tag)
            dismiss()
        }
        binding.pdfView.clicks(withAnim = false) {
            val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
            pdfIntent.type = "application/pdf"
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, 12)
        }
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            12 -> if (resultCode == RESULT_OK) {
                pdfUri = data?.data!!
                val uri: Uri = data.data!!
                val uriString: String = uri.toString()
                var pdfName: String?
                if (uriString.startsWith("content://")) {
                    var myCursor: Cursor? = null
                    try {
                        myCursor =
                            requireActivity().contentResolver.query(uri, null, null, null, null)
                        if (myCursor != null && myCursor.moveToFirst()) {
                            pdfName =
                                myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            listProject.add(pdfName)
                            projectViewModel.updateFile(idProject, listProject)
                            dismiss()
                        }
                    } finally {
                        myCursor?.close()
                    }
                }
            }
        }
    }
}