package com.hermanek.moviebrowserdemo.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject


/**
 * Created by jhermanek on 14.09.2021.
 */

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val detailResponse: MutableLiveData<Call<Movie>> = MutableLiveData()
    val movie: MutableLiveData<Movie> = MutableLiveData()
    var cachedMovie: MutableLiveData<Movie> = MutableLiveData()

    fun changeMovie(movie: Movie) {
        this.movie.value = movie
    }

    fun getMovieDetail(id: Int, language: String) {
        val detailResponse = repository.getMovieDetail(id, language)
        this.detailResponse.value = detailResponse
    }

    fun getMovieDetailFromDatabase(id: Int, language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cachedMovie.postValue(repository.getMovieDetailFromDatabase(id, language))
        }
    }


    fun addMovieDetail(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMovieDetailToDatabase(movie)
        }
    }
}