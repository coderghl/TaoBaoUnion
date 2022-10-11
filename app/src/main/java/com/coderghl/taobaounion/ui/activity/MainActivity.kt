package com.coderghl.taobaounion.ui.activity

import com.coderghl.taobaounion.R
import com.coderghl.taobaounion.base.BaseActivity
import com.coderghl.taobaounion.databinding.ActivityMainBinding
import com.coderghl.taobaounion.ui.fragment.HalfFragment
import com.coderghl.taobaounion.ui.fragment.RecommendFragment
import com.coderghl.taobaounion.ui.fragment.SearchFragment
import com.coderghl.taobaounion.ui.fragment.homefragment.HomeFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var currentPageIndex = 0

    private val currentPage = arrayOf(
        HomeFragment(),
        RecommendFragment(),
        HalfFragment(),
        SearchFragment()
    )

    override fun onCreate() {
        initPage()
        binding.navView.setOnItemSelectedListener {
            currentPageIndex = when (it.itemId) {
                R.id.navigation_home -> 0
                R.id.navigation_recommend -> 1
                R.id.navigation_half -> 2
                R.id.navigation_search -> 3
                else -> 0
            }
            switchPage()
            true
        }
    }

    /**
     * 初始化页面
     */
    private fun initPage() {
        val transaction = supportFragmentManager.beginTransaction()
        currentPage.forEach {
            transaction.add(R.id.fragment_container, it)
        }
        currentPage.forEach {
            transaction.hide(it)
        }
        transaction.show(currentPage.first())
        transaction.commit()
    }

    /**
     * 切换页面
     */
    private fun switchPage() {
        val transaction = supportFragmentManager.beginTransaction()
        currentPage.forEach {
            transaction.hide(it)
        }
        transaction.show(currentPage[currentPageIndex])
        transaction.commit()
    }

    override fun release() {

    }

    override fun initPresenter() {

    }
}