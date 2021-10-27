package com.hermanek.moviebrowserdemo.network

import com.hermanek.moviebrowserdemo.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by jhermanek on 07.09.2021.
 */

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val API: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }
}