package com.hermanek.moviebrowserdemo.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hermanek.moviebrowserdemo.model.Changes
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.network.CommonResponseError
import com.hermanek.moviebrowserdemo.network.MoviesResponse
import com.hermanek.moviebrowserdemo.repository.Repository
import retrofit2.Call
import retrofit2.Response

class MoviesViewModel(private val repository: Repository) : ViewModel() {

    val movieResponse: MutableLiveData<MoviesResponse> = MutableLiveData()

    fun getAllChanges() {
        repository.getAllChanges().enqueue(object : retrofit2.Callback<Changes> {
            var resp = MoviesResponse()
            override fun onResponse(call: Call<Changes>, response: Response<Changes>) {
                if (response.isSuccessful) {
                    resp.movies = removeAdultMovies(response.body()?.movies)
                    movieResponse.value = resp
                } else {
                    resp.error =
                        CommonResponseError("Response not ok; code:" + response.code(), null)
                }
            }

            override fun onFailure(call: Call<Changes>, t: Throwable) {
                resp.error = t as CommonResponseError
                movieResponse.value = resp
            }
        })
    }

    fun getChangesFromLastDays(days: Int) {
        repository.getChangesFromLastDays(days).enqueue(object : retrofit2.Callback<Changes> {
            var resp = MoviesResponse()
            override fun onResponse(call: Call<Changes>, response: Response<Changes>) {
                if (response.isSuccessful) {
                    resp.movies = removeAdultMovies(response.body()?.movies)
                    movieResponse.value = resp
                } else {
                    resp.error =
                        CommonResponseError("Response not ok; code:" + response.code(), null)
                }
            }

            override fun onFailure(call: Call<Changes>, t: Throwable) {
                resp.error = t as CommonResponseError
                movieResponse.value = resp
            }
        })
    }

    private fun removeAdultMovies(movies: List<Movie>?): List<Movie>? {
        return movies?.filter { e -> !e.adult }?.toList()
    }
}