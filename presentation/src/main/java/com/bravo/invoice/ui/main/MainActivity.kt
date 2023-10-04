package com.bravo.invoice.ui.main

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bravo.basic.extensions.clicks
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.R
import com.bravo.invoice.databinding.ActivityMainBinding
import com.bravo.invoice.dialogs.ConfirmDialog
import com.bravo.invoice.dialogs.InformationDialog
import com.bravo.invoice.ui.more.MoreFragment
import com.uber.autodispose.android.lifecycle.scope
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.subjects.BehaviorSubject
import com.uber.autodispose.autoDispose
import com.bravo.invoice.ui.client.ClientsFragment
import com.bravo.invoice.ui.items.ItemsFragment
import com.bravo.invoice.ui.items.add_item.AddItemFragment
import com.bravo.invoice.ui.main.invoices.InvoicesFragment
import io.reactivex.subjects.Subject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val tabClicks: Subject<Int> by lazy { BehaviorSubject.createDefault(2) }
    private var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
    @SuppressLint("CommitTransaction")
    override fun initObservable() {
        tabClicks
            .autoDispose(scope())
            .subscribe { index ->
               if(index != currentIndex){
                   val selectedFragment = when (index) {
                       1 -> ClientsFragment()
                       2 -> InvoicesFragment()
                       4 -> MoreFragment()
                       else -> null
                   }
                   if (selectedFragment != null) {
                       supportFragmentManager.beginTransaction()
                           .replace(R.id.fragment_container_view, selectedFragment)
                           .commit()
                   }
                   updateTabSelection(index)
                   currentIndex = index
               }

            }

    }
    private fun updateTabSelection(selectedIndex: Int) {
        tabBottoms.forEachIndexed { index, tab ->
            val isSelected = index == selectedIndex
            tab.display.isSelected = isSelected
            tab.image.isSelected = isSelected
            tab.topDash.isSelected = isSelected
        }
    }
    override fun initView() {
    }
    override fun initListener() {
        tabBottoms.forEachIndexed { index, tab ->
            tab.apply {
                display.setTextColor(colorStateList)
                image.imageTintList = colorStateList
                topDash.imageTintList = topColorStateList
                viewClicks.clicks(withAnim = false) {
                    tabClicks.onNext(index)
                }
            }
        }
        updateTabSelection(0)
    }
    private val tabBottoms by lazy {
        listOf(
            Tab(binding.tabBottom1, binding.ivIcon1, binding.tvMenu1, binding.ivTopLine1),
            Tab(binding.tabBottom2, binding.ivIcon2, binding.tvMenu2, binding.ivTopLine2),
            Tab(binding.tabBottom3, binding.ivIcon3, binding.tvMenu3, binding.ivTopLine3),
            Tab(binding.tabBottom4, binding.ivIcon4, binding.tvMenu4, binding.ivTopLine4),
            Tab(binding.tabBottom5, binding.ivIcon5, binding.tvMenu5, binding.ivTopLine5)
        )
    }


    private data class Tab(
        val viewClicks: View,
        val image: AppCompatImageView,
        val display: TextView,
        val topDash : AppCompatImageView
    )
    private val colorStateList by lazy {
        ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_selected),
                intArrayOf(-android.R.attr.state_selected)
            ),
            intArrayOf(
                this.getColor(R.color.blue_button),
                this.getColor(R.color.main_menu_text)
            )
        )
    }
    private val topColorStateList by lazy {
        ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_selected),
                intArrayOf(-android.R.attr.state_selected)
            ),
            intArrayOf(
                this.getColor(R.color.blue_button),
                this.getColor(R.color.white)
            )
        )
    }

    override fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(com.bravo.basic.R.anim.slide_up, 0, 0, com.bravo.basic.R.anim.slide_out_down )
            .add(R.id.fragment_container_view, fragment)
            .addToBackStack(null)
            .commit()
        visibleBottomLayout(false)
    }

    override fun popBackStack(isVisibleNavBar : Boolean) {
        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStack()
        visibleBottomLayout(isVisibleNavBar)
    }

    override fun popBackStack(fragment : String){
        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStack(fragment, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    override fun visibleBottomLayout(isVisible: Boolean) {
        binding.layoutBottom.isVisible = isVisible
    }

    override fun showConfirmDialog(
        title: Int,
        content: Int,
        positiveText: Int,
        negativeText: Int,
        positiveCallback: () -> Unit,
        negativeCallback: () -> (() -> Unit)?
    ) {

    }

    override fun showConfirmDialog(
        title: String,
        content: String,
        positiveText: String,
        negativeText: String,
        positiveCallback: () -> Unit,
        negativeCallback: (() -> Unit)?
    ) {
        val dialog = ConfirmDialog(
            title,
            content,
            positiveText,
            negativeText,
            positiveCallback,
            negativeCallback
        )
        dialog.show(supportFragmentManager, "")
    }

    override fun showInfoDialog(title: String, content: String, confirmText: String) {
        val dialog = InformationDialog(
            title,
            content,
            confirmText
        )
        dialog.show(supportFragmentManager, "")
    }

    override fun showInfoDialog(title: Int, content: Int, confirmText: Int) {
        val dialog = InformationDialog(
            resources.getString(title),
            resources.getString(content),
            resources.getString(confirmText),
        )
        dialog.show(supportFragmentManager, "")
    }
}

