package com.coderghl.taobaounion.model

import com.coderghl.taobaounion.model.bean.HomeCategories
import com.coderghl.taobaounion.model.bean.HomePagerContent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface Api {

    @GET("discovery/categories")
    fun getCategories(): Call<HomeCategories>

    @GET
    fun getHomePageContent(@Url url: String): Call<HomePagerContent>
}