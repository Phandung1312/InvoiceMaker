package com.bravo.invoice.ui.main

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.bravo.basic.view.BaseActivity
import com.bravo.invoice.R
import com.bravo.invoice.common.ConfigApp
import com.bravo.invoice.databinding.ActivityMainBinding
import com.bravo.invoice.ui.client.AddClientFragment
import com.bravo.invoice.ui.more.MoreFragment
import com.uber.autodispose.android.lifecycle.scope
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import com.uber.autodispose.autoDispose
import com.bravo.basic.base.LsPageAdapter
import com.bravo.basic.extensions.clicks
import com.bravo.basic.extensions.setTint
import com.bravo.invoice.ui.client.ClientsFragment
import io.reactivex.subjects.Subject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    @Inject
    lateinit var configApp: ConfigApp
    private val fragments by lazy { listOf(ClientsFragment(), MoreFragment()) }
    private val tabClicks: Subject<Int> by lazy { BehaviorSubject.createDefault(0) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun initListener() {

    }


    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun initObservable() {
        tabClicks
            .autoDispose(scope())
            .subscribe { index ->
                val selectedFragment = when (index) {
                    1 -> ClientsFragment()
                    4 -> MoreFragment()
                    else -> null
                }
                if (selectedFragment != null) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, selectedFragment)
                        .commit()
                }

                changeTitleTabColor(index)
            }

    }

    private fun changeTitleTabColor(index: Int) {
        when (index) {
            0 -> {
                binding.tvMenu1.changeTextColorSelected(true)
                binding.tvMenu2.changeTextColorSelected(false)
                binding.tvMenu3.changeTextColorSelected(false)
                binding.tvMenu4.changeTextColorSelected(false)
                binding.tvMenu5.changeTextColorSelected(false)

                binding.ivIcon1.changeImageSelected(true)
                binding.ivIcon2.changeImageSelected(false)
                binding.ivIcon3.changeImageSelected(false)
                binding.ivIcon4.changeImageSelected(false)
                binding.ivIcon5.changeImageSelected(false)

            }

            1 -> {
                binding.tvMenu1.changeTextColorSelected(false)
                binding.tvMenu2.changeTextColorSelected(true)
                binding.tvMenu3.changeTextColorSelected(false)
                binding.tvMenu4.changeTextColorSelected(false)
                binding.tvMenu5.changeTextColorSelected(false)

                binding.ivIcon1.changeImageSelected(false)
                binding.ivIcon2.changeImageSelected(true)
                binding.ivIcon3.changeImageSelected(false)
                binding.ivIcon4.changeImageSelected(false)
                binding.ivIcon5.changeImageSelected(false)

            }

            2 -> {
                binding.tvMenu1.changeTextColorSelected(false)
                binding.tvMenu2.changeTextColorSelected(false)
                binding.tvMenu3.changeTextColorSelected(true)
                binding.tvMenu4.changeTextColorSelected(false)
                binding.tvMenu5.changeTextColorSelected(false)

                binding.ivIcon1.changeImageSelected(false)
                binding.ivIcon2.changeImageSelected(false)
                binding.ivIcon3.changeImageSelected(true)
                binding.ivIcon4.changeImageSelected(false)
                binding.ivIcon5.changeImageSelected(false)
            }

            3 -> {
                binding.tvMenu1.changeTextColorSelected(false)
                binding.tvMenu2.changeTextColorSelected(false)
                binding.tvMenu3.changeTextColorSelected(false)
                binding.tvMenu4.changeTextColorSelected(true)
                binding.tvMenu5.changeTextColorSelected(false)

                binding.ivIcon1.changeImageSelected(false)
                binding.ivIcon2.changeImageSelected(false)
                binding.ivIcon3.changeImageSelected(false)
                binding.ivIcon4.changeImageSelected(true)
                binding.ivIcon5.changeImageSelected(false)
            }

            4 -> {
                binding.tvMenu1.changeTextColorSelected(false)
                binding.tvMenu2.changeTextColorSelected(false)
                binding.tvMenu3.changeTextColorSelected(false)
                binding.tvMenu4.changeTextColorSelected(false)
                binding.tvMenu5.changeTextColorSelected(true)

                binding.ivIcon1.changeImageSelected(false)
                binding.ivIcon2.changeImageSelected(false)
                binding.ivIcon3.changeImageSelected(false)
                binding.ivIcon4.changeImageSelected(false)
                binding.ivIcon5.changeImageSelected(true)
            }

        }
    }

    override fun initView() {
        tabBottoms.forEachIndexed { index, tab ->
            tab.viewClicks.clicks {
                tabClicks.onNext(index)
            }
        }
    }

    private val tabBottoms by lazy {
        listOf(
            Tab(binding.tabBottom1, binding.ivIcon1, binding.tvMenu1),
            Tab(binding.tabBottom2, binding.ivIcon2, binding.tvMenu2),
            Tab(binding.tabBottom3, binding.ivIcon3, binding.tvMenu3),
            Tab(binding.tabBottom4, binding.ivIcon4, binding.tvMenu4),
            Tab(binding.tabBottom5, binding.ivIcon5, binding.tvMenu5)
        )
    }


    private data class Tab(
        val viewClicks: View,
        val image: AppCompatImageView,
        val display: TextView
    )

    private fun TextView.changeTextColorSelected(isSelected: Boolean) {
        when {
            isSelected -> {
                this.setTextColor(context.getColor(R.color.blue_button))
            }

            else -> {
                this.setTextColor(context.getColor(R.color.main_menu_text))
            }
        }
    }

    private fun ImageView.changeImageSelected(isSelected: Boolean) {
        when {
            isSelected -> {
                this.setTint(context.getColor(R.color.blue_button))
            }

            else -> {
                this.setTint(context.getColor(R.color.main_menu_text))
            }
        }
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun backFragment() {
        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStack()
//        fragmentManager.popBackStack("fragment_name", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}