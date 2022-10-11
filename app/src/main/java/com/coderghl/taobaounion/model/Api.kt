package com.coderghl.taobaounion.model

import com.coderghl.taobaounion.model.bean.HomeCategories
import com.coderghl.taobaounion.model.bean.HomePagerContent
import com.coderghl.taobaounion.model.bean.TicketParams
import com.coderghl.taobaounion.model.bean.TicketResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface Api {

    @GET("discovery/categories")
    fun getCategories(): Call<HomeCategories>

    @GET
    fun getHomePageContent(@Url url: String): Call<HomePagerContent>

    @POST("tpwd")
    fun getTicket(@Body ticketParams: TicketParams): Call<TicketResult>
}