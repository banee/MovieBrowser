package com.hermanek.moviebrowserdemo.repository

import android.widget.Toast
import com.hermanek.moviebrowserdemo.R
import com.hermanek.moviebrowserdemo.model.Changes
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.network.MoviesResponse
import com.hermanek.moviebrowserdemo.network.RetrofitInstance
import com.hermanek.moviebrowserdemo.util.AppUtils
import retrofit2.Call
import retrofit2.Response
import java.util.*


/**
 * Created by jhermanek on 07.09.2021.
 */

class Repository {

    fun getAllChanges(): Call<Changes> {
        return RetrofitInstance.API.getAllChanges()
    }

    fun getMovieDetail(id: Int): Call<Movie> {
        return RetrofitInstance.API.getMovieDetail(id)
    }

    fun getChangesFromLastDays(days: Int): Call<Changes> {
        return RetrofitInstance.API.getChangesUsingStartDate(
            AppUtils.convertToDateFormatWithOffset(
                Date(),
                -days
            )
        )
    }


}