package com.coderghl.taobaounion.presenter


interface IHomePagerContentPresenter {
    /**
     * 根据分类Id获取内容
     */
    fun getContentByCategoryId(categoryId: Int)

    /**
     * 加载更多
     */
    fun loadMore(categoryId: Int,isRefresh: Boolean = false)

    /**
     * 重新加载
     */
    fun retry(categoryId: Int)

    /**
     * 刷新数据
     */
    fun onRefresh(categoryId: Int)

}