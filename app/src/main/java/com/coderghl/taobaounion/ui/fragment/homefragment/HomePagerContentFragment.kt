package com.coderghl.taobaounion.ui.fragment.homefragment

import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.coderghl.taobaounion.R
import com.coderghl.taobaounion.base.BaseFragment
import com.coderghl.taobaounion.databinding.FragmentHomePagerBinding
import com.coderghl.taobaounion.enum.NetworkState
import com.coderghl.taobaounion.model.bean.HomeCategories
import com.coderghl.taobaounion.model.bean.HomePagerContent
import com.coderghl.taobaounion.presenter.impl.HomePagerContentPresenterImpl
import com.coderghl.taobaounion.ui.adapter.HomeBannerAdapter
import com.coderghl.taobaounion.ui.adapter.HomePagerContentListAdapter
import com.coderghl.taobaounion.utils.LogUtils
import com.coderghl.taobaounion.utils.dp2px
import com.coderghl.taobaounion.view.IHomePagerContentCallback
import java.util.*

class HomePagerContentFragment(private val data: HomeCategories.Data) :
    BaseFragment<FragmentHomePagerBinding>(), IHomePagerContentCallback {

    private var presenter: HomePagerContentPresenterImpl? = null
    private var homePagerContentListAdapter = HomePagerContentListAdapter()
    private var homeBannerAdapter = HomeBannerAdapter()
    private var bannerTimer: Timer? = null

    /**
     * banner 滑动事件监听
     */
    private var bannerPageCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.bannerIndicator.forEach {
                it.setBackgroundResource(R.drawable.shape_banner_indicaotr)
            }

            binding.bannerIndicator.getChildAt(position)
                .setBackgroundResource(R.drawable.shape_banner_indicaotr_action)
        }
    }

    /**
     * 下拉刷新事件监听
     */
    private var refreshListener = SwipeRefreshLayout.OnRefreshListener {

    }

    override fun onViewCreated() {
        binding.homePagerList.layoutManager = LinearLayoutManager(requireContext())
        binding.homePagerList.adapter = homePagerContentListAdapter
        binding.banner.adapter = homeBannerAdapter
        binding.banner.registerOnPageChangeCallback(bannerPageCallback)
        binding.refreshLayout.setColorSchemeColors(requireContext().getColor(R.color.purple_500))
        binding.refreshLayout.setOnRefreshListener(refreshListener)

        LogUtils.d(this, "title: ${data.title}")
        LogUtils.d(this, "id: ${data.id}")
    }

    /**
     * 初始化Presenter
     */
    override fun initPresenter() {
        presenter = HomePagerContentPresenterImpl()
        presenter?.registerCallback(this)
    }

    /**
     * 释放资源
     */
    override fun release() {
        bannerTimer?.cancel()
        bannerTimer = null
        binding.banner.unregisterOnPageChangeCallback(bannerPageCallback)
        presenter?.unRegisterCallback(this)
    }

    /**
     * 加载数据
     */
    override fun loadData() {
        presenter?.getContentByCategoryId(data.id)
    }

    /**
     * 获取数据成功
     */
    override fun onContentSuccess(contents: List<HomePagerContent.Data>) {
        switchState(NetworkState.SUCCESS, binding.container)
        homePagerContentListAdapter.initListData(contents)
    }


    /**
     * 加载轮播图数据成功
     */
    override fun onLoopBannerSuccess(contents: List<HomePagerContent.Data>) {
        homeBannerAdapter.initBannerData(contents)
        initBannerIndicator()
        startLoopBanner()
    }

    /**
     * 启动轮播图
     */
    private fun startLoopBanner() {
        bannerTimer = Timer()
        bannerTimer?.schedule(
            object : TimerTask() {
                override fun run() {
                    if (binding.banner.currentItem + 1 < homeBannerAdapter.itemCount) {
                        binding.banner.currentItem += 1
                    } else {
                        requireActivity().runOnUiThread {
                            binding.banner.setCurrentItem(0, true)
                        }
                    }
                }
            }, 2000, 2000
        )
    }

    /**
     * 初始化banner indicator
     */
    private fun initBannerIndicator() {
        binding.bannerIndicator.removeAllViews()
        val layoutParams = LinearLayoutCompat.LayoutParams(8.dp2px(requireContext()).toInt(), 8.dp2px(requireContext()).toInt())
        layoutParams.setMargins(4.dp2px(requireContext()).toInt(), 0, 8.dp2px(requireContext()).toInt(), 0)
        for (i in 0 until homeBannerAdapter.itemCount) {
            val indicator = View(requireContext())
            indicator.layoutParams = layoutParams
            indicator.setBackgroundResource(R.drawable.shape_banner_indicaotr)
            binding.bannerIndicator.addView(indicator)
        }
    }


    /**
     * 加载更多
     */
    override fun onLoadMore() {

    }

    /**
     * 加载更多成功
     */
    override fun onLoadMoreSuccess(contents: List<HomePagerContent.Data>) {

    }


    /**
     * 加载更多数据为空
     */
    override fun onLoadMoreEmpty() {
    }

    /**
     * 加载更多数据失败
     */
    override fun onLoadMoreError() {
    }

    /**
     * 正在加载
     */
    override fun onLoading() {
        switchState(NetworkState.LOADING, binding.container)
    }

    /**
     * 数据为空
     */
    override fun onEmpty() {
        switchState(NetworkState.EMPTY, binding.container)
    }

    /**
     * 请求失败
     */
    override fun onError() {
        switchState(NetworkState.ERROR, binding.container)
    }

    /**
     * 无网络链接
     */
    override fun onNoNetwork() {
        switchState(NetworkState.NO_NETWORK, binding.container)
    }

    /**
     * 请求失败时点击重试
     */
    override fun retryTap() {
        presenter?.getContentByCategoryId(data.id)
    }
}