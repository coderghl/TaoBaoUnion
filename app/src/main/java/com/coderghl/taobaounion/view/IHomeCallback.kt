package com.coderghl.taobaounion.view

import com.coderghl.taobaounion.base.IBaseCallback
import com.coderghl.taobaounion.model.bean.HomeCategories

/**
 * 通知接口
 */
interface IHomeCallback : IBaseCallback {

    /**
     * 请求分类成功
     * */
    fun onCategoriesSuccess(homeCategories: HomeCategories)
}