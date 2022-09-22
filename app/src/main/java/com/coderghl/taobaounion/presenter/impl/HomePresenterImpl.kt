package com.coderghl.taobaounion.presenter.impl

import com.coderghl.taobaounion.base.IBasePresenter
import com.coderghl.taobaounion.model.bean.HomeCategories
import com.coderghl.taobaounion.presenter.IHomePresenter
import com.coderghl.taobaounion.utils.LogUtils
import com.coderghl.taobaounion.utils.RetrofitManager
import com.coderghl.taobaounion.view.IHomeCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

open class HomePresenterImpl : IHomePresenter, IBasePresenter<IHomeCallback> {
    private var mCallback: IHomeCallback? = null

    override fun getCategories() {
        mCallback?.onLoading()

        RetrofitManager.get().getApi().getCategories().also {
            it.enqueue(object : Callback<HomeCategories> {
                override fun onResponse(call: Call<HomeCategories>, response: Response<HomeCategories>) {
                    val code = response.code()
                    LogUtils.d(this, "ResultCode: $code")

                    if (code == HttpURLConnection.HTTP_OK) {
                        // 请求成功
                        val categories = response.body()
                        LogUtils.d(this, categories.toString())

                        if (categories == null || categories.data.size == 0) {
                            mCallback?.onEmpty()
                        } else {
                            mCallback?.onCategoriesSuccess(categories)
                        }
                    } else {
                        // 请求失败
                        LogUtils.i(this, "请求失败..")
                        mCallback?.onError()
                    }
                }

                override fun onFailure(call: Call<HomeCategories>, t: Throwable) {
                    LogUtils.e(this, "请求错误...")
                    mCallback?.onNoNetwork()
                }
            })
        }
    }

    override fun registerCallback(callback: IHomeCallback) {
        this.mCallback = callback
    }

    override fun unRegisterCallback(callback: IHomeCallback) {
        mCallback = null
    }
}