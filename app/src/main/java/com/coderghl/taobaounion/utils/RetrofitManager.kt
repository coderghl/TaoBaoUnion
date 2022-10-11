package com.coderghl.taobaounion.utils

import com.coderghl.taobaounion.model.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager private constructor() {
    private var retrofit: Retrofit? = null

    init {
        /**
         *  创建Retrofit
         * */
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }


    companion object {
        private const val BASE_URL: String = "https://api.sunofbeaches.com/shop/"

        private var instance: RetrofitManager? = null
            get() {
                if (field == null) {
                    field = RetrofitManager()
                }
                return field
            }

        fun get(): RetrofitManager {
            return instance!!
        }
    }

    fun getApi() = retrofit?.create(Api::class.java)!!
}