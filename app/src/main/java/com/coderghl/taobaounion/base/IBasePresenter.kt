package com.coderghl.taobaounion.base

interface IBasePresenter<T> {

    /**
     * 注册回调接口
     */
    fun registerCallback(callback: T)

    /**
     * 注销回调接口
     */
    fun unRegisterCallback(callback: T)
}