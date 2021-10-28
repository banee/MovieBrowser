package com.hermanek.moviebrowserdemo.network

import com.hermanek.moviebrowserdemo.model.Movie

class MoviesResponse {
    var movies : List<Movie>? = ArrayList()
    var error : CommonResponseError? = null

}