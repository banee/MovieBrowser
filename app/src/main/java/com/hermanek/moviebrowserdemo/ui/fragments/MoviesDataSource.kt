package com.hermanek.moviebrowserdemo.ui.fragments

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.network.RetrofitService
import retrofit2.HttpException
import java.io.IOException


/**
 * Created by jhermanek on 28.02.2022.
 */

private const val FIRST_PAGE = 1;

class MoviesDataSource(
    private val service: RetrofitService, private val queryParams: HashMap<String, Any>
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: FIRST_PAGE
        return try {
            queryParams["page"] = position
            val response = service.getMovies(queryParams)
            val movies = response.results
            LoadResult.Page(
                data = movies,
                prevKey = if (position == FIRST_PAGE) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (ioEx: IOException) {
            LoadResult.Error(ioEx)
        } catch (httpEx: HttpException) {
            LoadResult.Error(httpEx)
        }
    }
}