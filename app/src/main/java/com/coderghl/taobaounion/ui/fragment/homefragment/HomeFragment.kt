package com.coderghl.taobaounion.ui.fragment.homefragment

import com.coderghl.taobaounion.base.BaseFragment
import com.coderghl.taobaounion.databinding.FragmentHomeBinding
import com.coderghl.taobaounion.enum.NetworkState
import com.coderghl.taobaounion.model.bean.HomeCategories
import com.coderghl.taobaounion.presenter.impl.HomePresenterImpl
import com.coderghl.taobaounion.ui.adapter.HomePagerAdapter
import com.coderghl.taobaounion.view.IHomeCallback
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>(), IHomeCallback {
    private lateinit var homePagerAdapter: HomePagerAdapter
    private var homePresenterImpl: HomePresenterImpl? = null

    override fun onViewCreated() {
        homePagerAdapter = HomePagerAdapter(requireActivity())
        binding.viewpager.adapter = homePagerAdapter
    }

    /** 创建Presenter */
    override fun initPresenter() {
        homePresenterImpl = HomePresenterImpl().also {
            it.registerCallback(this)
        }
    }

    /**
     * 释放资源
     * */
    override fun release() {
        homePresenterImpl?.unRegisterCallback(this)
    }


    /**
     * 加载数据
     * */
    override fun loadData() {
        homePresenterImpl?.getCategories()
    }


    /**
     * 分类数据请求成功回调
     * */
    override fun onCategoriesSuccess(homeCategories: HomeCategories) {
        switchState(NetworkState.SUCCESS, binding.container)
        homePagerAdapter.setData(homeCategories)
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = homeCategories.data[position].title
        }.attach()
    }

    /**
     * 无网络
     */
    override fun onNoNetwork() {
        switchState(NetworkState.NO_NETWORK, binding.container)
    }

    /**
     * 加载中
     */
    override fun onLoading() {
        switchState(NetworkState.LOADING, binding.container)
    }

    /**
     * 请求数据失败
     * */
    override fun onError() {
        switchState(NetworkState.ERROR, binding.container)
    }


    /**
     * 请求数据为空
     */
    override fun onEmpty() {
        switchState(NetworkState.EMPTY, binding.container)
    }

    /**
     * 请求数据失败重试 重新加载分类
     */
    override fun retryTap() {
        homePresenterImpl?.getCategories()
    }
}