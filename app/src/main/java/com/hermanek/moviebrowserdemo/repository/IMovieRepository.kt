package com.hermanek.moviebrowserdemo.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.hermanek.moviebrowserdemo.model.Movie
import retrofit2.Call


/**
 * Created by jhermanek on 02.03.2022.
 */

interface IMovieRepository {

    fun getMovieDetail(id: Int, language: String): Call<Movie>

    suspend fun getMovieDetailFromDatabase(id: Int, language: String): Movie

    suspend fun addMovieDetailToDatabase(movie: Movie)

    fun getMovies(queryParams: HashMap<String, Any>): LiveData<PagingData<Movie>>

}