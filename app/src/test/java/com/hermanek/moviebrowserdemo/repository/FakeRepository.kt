package com.hermanek.moviebrowserdemo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.hermanek.moviebrowserdemo.model.Movie
import retrofit2.Call


/**
 * Created by jhermanek on 02.03.2022.
 */

class FakeRepository : IMovieRepository {

    private val movieItems = mutableListOf<Movie>()

    private val observableMovieItems = MutableLiveData<List<Movie>>()
    private val observableMovieItem = MutableLiveData<Movie>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override fun getMovieDetail(id: Int, language: String): Call<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetailFromDatabase(id: Int, language: String): Movie {
        TODO("Not yet implemented")
    }

    override suspend fun addMovieDetailToDatabase(movie: Movie) {
        movieItems.add(movie)
    }

    override fun getMovies(queryParams: HashMap<String, Any>): LiveData<PagingData<Movie>> {
        TODO("Not yet implemented")
    }
}