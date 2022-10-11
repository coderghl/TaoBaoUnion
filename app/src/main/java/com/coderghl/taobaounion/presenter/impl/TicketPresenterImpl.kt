package com.coderghl.taobaounion.presenter.impl

import com.coderghl.taobaounion.model.bean.TicketParams
import com.coderghl.taobaounion.model.bean.TicketResult
import com.coderghl.taobaounion.presenter.ITicketPresenter
import com.coderghl.taobaounion.utils.RetrofitManager
import com.coderghl.taobaounion.view.ITicketPagerCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class TicketPresenterImpl : ITicketPresenter {
    private var mCallback: ITicketPagerCallback? = null

    override fun getTicket(title: String, url: String, cover: String) {
        val task = RetrofitManager.get().getApi().getTicket(TicketParams("https:${url}", title))
        task.enqueue(object : Callback<TicketResult> {
            override fun onResponse(call: Call<TicketResult>, response: Response<TicketResult>) {
                val code = response.code()
                if (code == HttpURLConnection.HTTP_OK) {
                    val result = response.body()
                    if (result != null) {
                        mCallback?.onTicketSuccess(cover, result)
                    } else {
                        mCallback?.onEmpty()
                    }
                } else {
                    mCallback?.onError()
                }
            }

            override fun onFailure(call: Call<TicketResult>, t: Throwable) {
                mCallback?.onNoNetwork()
            }
        })
    }

    override fun registerCallback(callback: ITicketPagerCallback) {
        mCallback = callback
    }

    override fun unRegisterCallback(callback: ITicketPagerCallback) {
        mCallback = null
    }
}