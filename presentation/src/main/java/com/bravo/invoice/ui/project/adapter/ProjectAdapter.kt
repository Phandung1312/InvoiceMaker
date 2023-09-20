package com.bravo.invoice.ui.project.adapter


import com.bravo.basic.extensions.clicks
import com.bravo.domain.model.Project
import com.bravo.invoice.databinding.CustomShowProject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class ProjectAdapter  @Inject constructor() :
    com.bravo.basic.view.BaseAdapter<Project, CustomShowProject>(CustomShowProject::inflate) {
    val itemClicksProject : Subject<Project> = PublishSubject.create()
    override fun bindItem(item: Project, binding: CustomShowProject, position: Int) {
        binding.itemShowProject = item
        binding.root.clicks(withAnim = false) {
            itemClicksProject.onNext(item)
        }
    }
}