package com.hermanek.moviebrowserdemo.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hermanek.moviebrowserdemo.model.Changes
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.repository.Repository
import retrofit2.Call

class MoviesViewModel(private val repository: Repository) : ViewModel() {

    val listData: MutableLiveData<List<Movie>> = MutableLiveData()

    val changesResponse: MutableLiveData<Call<Changes>> = MutableLiveData()


    fun getAllChanges() {
        val changesResponse = repository.getAllChanges()
        this.changesResponse.value = changesResponse
    }

    fun getChangesFromLastDays(days: Int) {
        val changesResponse = repository.getChangesFromLastDays(days)
        this.changesResponse.value = changesResponse
    }

}