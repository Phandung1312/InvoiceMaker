package com.bravo.invoice.ui.project.adapter



import com.bravo.basic.extensions.clicks
import com.bravo.domain.model.Project
import com.bravo.invoice.R
import com.bravo.invoice.databinding.Custom_checked_project
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class AdapterCheckedProject @Inject constructor() :
    com.bravo.basic.view.BaseAdapter<Project, Custom_checked_project>(Custom_checked_project::inflate) {
     val itemClicksProject: Subject<Project> = PublishSubject.create()
    override fun bindItem(item: Project, binding: Custom_checked_project, position: Int) {
        binding.itemCheckedProject = item
        binding.root.clicks(withAnim = false) {
            itemClicksProject.onNext(item)
            item.isChecked = !item.isChecked
            notifyDataSetChanged()
        }
        binding.checkItemImg.setImageResource(if (item.isChecked) R.drawable.ic_checked else R.drawable.ic_uncheck)



    }


}
