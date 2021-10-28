package com.hermanek.moviebrowserdemo.ui.fragments

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hermanek.moviebrowserdemo.R
import com.hermanek.moviebrowserdemo.model.Changes
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.network.CommonResponseError
import com.hermanek.moviebrowserdemo.network.MoviesResponse
import com.hermanek.moviebrowserdemo.repository.Repository
import retrofit2.Call
import retrofit2.Response


/**
 * Created by jhermanek on 14.09.2021.
 */

class MovieDetailViewModel(private val repository: Repository) : ViewModel() {

    val movieResponse: MutableLiveData<MoviesResponse> = MutableLiveData()

    fun getMovieDetail(id: Int) {
        if (id != -1) {
            repository.getMovieDetail(id).enqueue(object : retrofit2.Callback<Movie> {
                var resp = MoviesResponse()
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful) {
                        resp.movie = response.body() as Movie
                        movieResponse.value = resp
                    } else {
                        resp.error =
                            CommonResponseError("Response not ok; code:" + response.code(), null)
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    resp.error = t as CommonResponseError
                    movieResponse.value = resp
                }
            })
        }
    }
}