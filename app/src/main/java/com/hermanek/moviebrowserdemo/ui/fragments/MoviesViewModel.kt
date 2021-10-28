package com.hermanek.moviebrowserdemo.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hermanek.moviebrowserdemo.model.Changes
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.network.CommonResponseError
import com.hermanek.moviebrowserdemo.repository.Repository
import retrofit2.Call
import retrofit2.Response

class MoviesViewModel(private val repository: Repository) : ViewModel() {

    var movies: MutableLiveData<List<Movie>> = MutableLiveData()
    var errorResponse: MutableLiveData<CommonResponseError> = MutableLiveData()

    fun getAllChanges() {
        repository.getAllChanges().enqueue(object : retrofit2.Callback<Changes> {
            override fun onResponse(call: Call<Changes>, response: Response<Changes>) {
                if (response.isSuccessful) {
                    movies.value = removeAdultMovies(response.body()?.movies)
                } else {
                    errorResponse.value =
                        CommonResponseError("Response not ok; code:" + response.code(), null)
                }
            }

            override fun onFailure(call: Call<Changes>, t: Throwable) {
                errorResponse.value = t as CommonResponseError
            }
        })
    }

    fun getChangesFromLastDays(days: Int) {
        repository.getChangesFromLastDays(days).enqueue(object : retrofit2.Callback<Changes> {
            override fun onResponse(call: Call<Changes>, response: Response<Changes>) {
                if (response.isSuccessful) {
                    movies.value = removeAdultMovies(response.body()?.movies)
                } else {
                    errorResponse.value =
                        CommonResponseError("Response not ok; code:" + response.code(), null)
                }
            }

            override fun onFailure(call: Call<Changes>, t: Throwable) {
                errorResponse.value = t as CommonResponseError
            }
        })
    }

    private fun removeAdultMovies(movies: List<Movie>?): List<Movie>? {
        return movies?.filter { e -> !e.adult }?.toList()
    }
}