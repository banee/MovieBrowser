package com.hermanek.moviebrowserdemo.model

import com.google.gson.annotations.SerializedName

data class Changes(
    val page: Int,

    @SerializedName("results")
    val movies: List<Movie>,

    val total_pages: Int,

    val total_results: Int
)