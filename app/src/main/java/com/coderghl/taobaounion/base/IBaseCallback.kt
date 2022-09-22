package com.coderghl.taobaounion.base

interface IBaseCallback {

    /**
     * 加载中
     */
    fun onLoading()


    /**
     * 请求到的数据为空
     */
    fun onEmpty()


    /**
     * 请求错误
     */
    fun onError()


    /**
     * 网络请求失败 无网络
     */
    fun onNoNetwork()
}