package com.hermanek.moviebrowserdemo.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.repository.Repository
import retrofit2.Call


/**
 * Created by jhermanek on 14.09.2021.
 */

class MovieDetailViewModel(private val repository: Repository) : ViewModel() {

    val detailResponse: MutableLiveData<Call<Movie>> = MutableLiveData()

    fun getMovieDetail(id: Int) {
        if (id != -1) {
            val detailResponse = repository.getMovieDetail(id)
            this.detailResponse.value = detailResponse
        }
    }
}