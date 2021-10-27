package com.hermanek.moviebrowserdemo.network

import com.hermanek.moviebrowserdemo.model.Changes
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.util.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by jhermanek on 06.09.2021.
 */

interface RetrofitService {

    @GET("movie/changes?api_key=$API_KEY")
    fun getAllChanges(): Call<Changes>

    @GET("movie/{id}?api_key=$API_KEY")
    fun getMovieDetail(@Path("id") id: Int): Call<Movie>

    @GET("movie/changes?api_key=$API_KEY")
    fun getChangesUsingStartDate(@Query("start_date") startDate: String): Call<Changes>
}