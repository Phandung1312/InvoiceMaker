package com.bravo.invoice.ui.project.adapter

import com.bravo.basic.extensions.clicks
import com.bravo.domain.model.Project
import com.bravo.invoice.databinding.CustomShowProject
import com.bravo.invoice.ui.project.SetData
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class ProjectAdapter @Inject constructor() :
    com.bravo.basic.view.BaseAdapter<Project, CustomShowProject>(CustomShowProject::inflate),
    SetData {
    val itemClicksProject: Subject<Project> = PublishSubject.create()
    var checkData: Boolean = false


    override fun bindItem(item: Project, binding: CustomShowProject, position: Int) {
        binding.itemShowProject = item
        binding.mainItem.clicks (withAnim = false){
            itemClicksProject.onNext(item)
        }
        binding.right1.clicks(withAnim = false) {
            callbackStatus?.invoke(item.id to position)
        }
        binding.right2.clicks(withAnim = false) {
            callback?.invoke(item.id to position)
        }
        if (checkData) {
            binding.right1.text = "Active"
        } else {
            binding.right1.text = "Reactive"
        }


    }


    var callback: ((Pair<Long, Int>) -> Unit)? = null
    var callbackStatus: ((Pair<Long, Int>) -> Unit)? = null
    override fun changeText(isActive: Boolean) {
        checkData = isActive
    }


}