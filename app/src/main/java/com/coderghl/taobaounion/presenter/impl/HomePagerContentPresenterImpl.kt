package com.coderghl.taobaounion.presenter.impl

import com.coderghl.taobaounion.base.IBasePresenter
import com.coderghl.taobaounion.model.bean.HomePagerContent
import com.coderghl.taobaounion.presenter.IHomePagerContentPresenter
import com.coderghl.taobaounion.utils.LogUtils
import com.coderghl.taobaounion.utils.RetrofitManager
import com.coderghl.taobaounion.view.IHomePagerContentCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class HomePagerContentPresenterImpl : IHomePagerContentPresenter, IBasePresenter<IHomePagerContentCallback> {
    companion object {
        const val DEFAULT_PAGE = 1
    }

    private val pageInfo = HashMap<Int, Int>()
    private var mCallback: IHomePagerContentCallback? = null
    private var isLoadingMore = false

    /**
     * 根据分类id加载内容
     */
    override fun getContentByCategoryId(categoryId: Int) {
        mCallback?.onLoading()
        pageInfo[categoryId] = DEFAULT_PAGE
        val url = getUrl(categoryId, pageInfo[categoryId] ?: DEFAULT_PAGE)

        LogUtils.d(this, "Url: ${RetrofitManager.BASE_URL}$url")

        val task = RetrofitManager.get().getApi().getHomePageContent(url)

        task.enqueue(object : Callback<HomePagerContent> {
            override fun onResponse(call: Call<HomePagerContent>, response: Response<HomePagerContent>) {
                val code = response.code()
                LogUtils.d(this@HomePagerContentPresenterImpl, "ResultCode: $code")

                if (code == HttpURLConnection.HTTP_OK) {
                    response.body()?.data?.let {
                        LogUtils.d(this@HomePagerContentPresenterImpl, "${it.size}")
                        // 更新ui
                        handleHomePagerContentResult(it)
                    }
                } else {
                    LogUtils.d(this@HomePagerContentPresenterImpl, "Error: 请求失败")
                    mCallback?.onError()
                }
            }

            override fun onFailure(call: Call<HomePagerContent>, t: Throwable) {
                LogUtils.e(this@HomePagerContentPresenterImpl, "onFailure: $t")
                mCallback?.onNoNetwork()
            }
        })
    }

    /**
     * 请求数据成功处理数据传给ui层并通知
     */
    private fun handleHomePagerContentResult(data: List<HomePagerContent.Data>) {
        // 从列表数据中拿取5条数据 当作轮播图数据
        val bannerList = data.subList(data.size - 5, data.size)

        if (data.isEmpty()) {
            mCallback?.onEmpty()
        } else {
            mCallback?.onContentSuccess(data)
            mCallback?.onLoopBannerSuccess(bannerList)
        }
    }


    /**
     * 加载更多
     */
    override fun loadMore(categoryId: Int) {

    }

    /**
     * 加载更多失败重新加载更多
     */
    override fun retry(categoryId: Int) {
    }

    /**
     * 注册回调
     */
    override fun registerCallback(callback: IHomePagerContentCallback) {
        this.mCallback = callback
    }

    /**
     * 注销回调
     */
    override fun unRegisterCallback(callback: IHomePagerContentCallback) {
        mCallback = null
    }


    /**
     * 获取url
     */
    private fun getUrl(categoryId: Int, pageIndex: Int) = "discovery/$categoryId/$pageIndex"
}