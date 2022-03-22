package com.hermanek.moviebrowserdemo.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.model.MovieDao
import com.hermanek.moviebrowserdemo.network.RetrofitService
import com.hermanek.moviebrowserdemo.ui.fragments.MoviesDataSource
import com.hermanek.moviebrowserdemo.util.AppUtils
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by jhermanek on 07.09.2021.
 */

@Singleton
class Repository @Inject constructor(
    private val moviesApi: RetrofitService,
    private val movieDao: MovieDao
) : IMovieRepository {

    override fun getMovieDetail(id: Int, language: String): Call<Movie> {
        return moviesApi.getMovieDetail(id, language)
    }

    override suspend fun getMovieDetailFromDatabase(id: Int, language: String): Movie {
        return movieDao.readMovie(id, language)
    }

    override suspend fun addMovieDetailToDatabase(movie: Movie) {
        movieDao.addMovie(movie)
    }

    override fun getMovies(queryParams: HashMap<String, Any>): LiveData<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ), pagingSourceFactory = { MoviesDataSource(moviesApi, queryParams) }).liveData
}