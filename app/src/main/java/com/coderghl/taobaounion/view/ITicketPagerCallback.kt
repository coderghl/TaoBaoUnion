package com.coderghl.taobaounion.view

import com.coderghl.taobaounion.base.IBaseCallback
import com.coderghl.taobaounion.model.bean.TicketResult

interface ITicketPagerCallback : IBaseCallback {
    /**
     * 淘口令加载成功
     */
    fun onTicketSuccess(cover: String, result: TicketResult)
}