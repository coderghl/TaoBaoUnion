package com.coderghl.taobaounion.ui.fragment.homefragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.coderghl.taobaounion.base.BaseFragment
import com.coderghl.taobaounion.databinding.FragmentHomePagerBinding
import com.coderghl.taobaounion.enum.NetworkState
import com.coderghl.taobaounion.model.bean.HomeCategories
import com.coderghl.taobaounion.model.bean.HomePagerContent
import com.coderghl.taobaounion.presenter.impl.HomePagerContentPresenterImpl
import com.coderghl.taobaounion.ui.adapter.HomeBannerAdapter
import com.coderghl.taobaounion.ui.adapter.HomePagerContentListAdapter
import com.coderghl.taobaounion.utils.LogUtils
import com.coderghl.taobaounion.view.IHomePagerContentCallback

class HomePagerContentFragment(private val data: HomeCategories.Data) :
    BaseFragment<FragmentHomePagerBinding>(), IHomePagerContentCallback {

    private var presenter: HomePagerContentPresenterImpl? = null

    private var homePagerContentListAdapter = HomePagerContentListAdapter()

    override fun onViewCreated() {
        binding.homePagerList.layoutManager = LinearLayoutManager(requireContext())
        binding.homePagerList.adapter = homePagerContentListAdapter

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
        homePagerContentListAdapter.initBannerData(contents)
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