package com.hermanek.moviebrowserdemo.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.network.CommonResponseError
import com.hermanek.moviebrowserdemo.repository.Repository
import retrofit2.Call
import retrofit2.Response


/**
 * Created by jhermanek on 14.09.2021.
 */

class MovieDetailViewModel(private val repository: Repository) : ViewModel() {
    val movieDetail: MutableLiveData<Movie> = MutableLiveData()
    var errorResponse: MutableLiveData<CommonResponseError> = MutableLiveData()

    fun getMovieDetail(id: Int) {
        if (id != -1) {
            repository.getMovieDetail(id).enqueue(object : retrofit2.Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful) {
                        movieDetail.value = response.body() as Movie
                    } else {
                        errorResponse.value = CommonResponseError("Response not ok; code:" + response.code(), null)
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    errorResponse.value = t as CommonResponseError
                }
            })
        }
    }
}