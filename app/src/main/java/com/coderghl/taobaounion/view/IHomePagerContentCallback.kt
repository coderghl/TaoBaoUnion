package com.coderghl.taobaounion.view

import com.coderghl.taobaounion.base.IBaseCallback
import com.coderghl.taobaounion.model.bean.HomePagerContent

interface IHomePagerContentCallback : IBaseCallback {

    /**
     * 请求成功
     */
    fun onContentSuccess(contents: List<HomePagerContent.Data>)

    /**
     * 加载更多成功
     */
    fun onLoadMoreSuccess(contents: List<HomePagerContent.Data>)


    /**
     * 首页轮播图
     */
    fun onLoopBannerSuccess(contents: List<HomePagerContent.Data>)


    /**
     * 加载更多是没有数据了
     */
    fun onLoadMoreEmpty()


    /**
     * 加载更多时网络错误
     */
    fun onLoadMoreError()

    /**
     * 刷新数据
     */
    fun onRefreshSuccess(contents: List<HomePagerContent.Data>)

}