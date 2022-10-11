package com.coderghl.taobaounion.presenter

import com.coderghl.taobaounion.base.IBasePresenter
import com.coderghl.taobaounion.view.ITicketPagerCallback

interface ITicketPresenter : IBasePresenter<ITicketPagerCallback> {
    /**
     * 获取优惠券生成淘口令
     */
    fun getTicket(title: String, url: String, cover: String)
}