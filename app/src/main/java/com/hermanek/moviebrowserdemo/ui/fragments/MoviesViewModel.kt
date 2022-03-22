package com.hermanek.moviebrowserdemo.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hermanek.moviebrowserdemo.repository.Repository
import com.hermanek.moviebrowserdemo.util.AppUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val queryParams =
        MutableLiveData(HashMap<String, Any>().apply {
            this["language"] = AppUtils.getSupportedLanguage()
        })

    var movies = queryParams.switchMap { queryParams ->
        repository.getMovies(queryParams).cachedIn(viewModelScope)
    }

    // TODO prepared for search filters
    fun getMovies(queryParams: HashMap<String, Any>?) {
        this.queryParams.value = queryParams;
    }
}