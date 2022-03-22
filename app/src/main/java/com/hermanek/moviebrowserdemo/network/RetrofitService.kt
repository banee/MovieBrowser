package com.hermanek.moviebrowserdemo.network

import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.model.MoviesResponse
import com.hermanek.moviebrowserdemo.util.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


/**
 * Created by jhermanek on 06.09.2021.
 */

interface RetrofitService {

    @GET("movie/{id}?api_key=$API_KEY&include_image_language=en,null")
    fun getMovieDetail(@Path("id") id: Int, @Query("language") language: String?): Call<Movie>

    @GET("discover/movie?api_key=$API_KEY")
    suspend fun getMovies(
        @QueryMap params: HashMap<String, Any>
    ): MoviesResponse

}