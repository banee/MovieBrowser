package com.hermanek.moviebrowserdemo.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.hermanek.moviebrowserdemo.R
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.repository.Repository
import com.hermanek.moviebrowserdemo.util.Constants.Companion.IMAGE_BASE_BACKDROP
import com.hermanek.moviebrowserdemo.util.Constants.Companion.IMAGE_BASE_POSTER
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

class MoviesAdapter :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private lateinit var mListener: onItemClickListener
    private val repository = Repository()

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    private var movieList: List<Movie> = ArrayList()

    fun updateData(movieList: List<Movie>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    fun getItemOnPosition(position: Int): Movie {
        return movieList[position]
    }

    inner class MovieViewHolder constructor(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val title: MaterialTextView = itemView.findViewById(R.id.movieTitle)
        val image: ImageView = itemView.findViewById(R.id.movieImage)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem: Movie = movieList[position]
        holder.title.text = movieItem.id.toString()

        var hasFullInfo = true
        when {
            movieItem.title?.isNotEmpty() != null -> {
                holder.title.text = movieItem.title
            }
            movieItem.original_title?.isNotEmpty() != null -> {
                holder.title.text = movieItem.original_title
            }
            else -> {
                hasFullInfo = false
            }
        }

        if (!hasFullInfo) {
            downloadItemData(movieItem, position, holder)
        }
    }

    private fun downloadItemData(movieItem: Movie, position: Int, holder: MovieViewHolder) {
        val movieDetail = repository.getMovieDetail(movieItem.id)
        movieDetail.enqueue(object : retrofit2.Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    val movie: Movie = response.body() as Movie
                    reloadItemData(movie, position, holder)
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
            }
        })
    }

    private fun reloadItemData(movie: Movie, position: Int, holder: MovieViewHolder) {
        val movieItem = movieList[position]
        movieList[position].title = movie.title
        movieList[position].original_title = movie.original_title
        movieList[position].backdrop_path = movie.backdrop_path
        movieList[position].poster_path = movie.poster_path

        if (movieItem.title?.isNotEmpty() != null) {
            holder.title.text = movieItem.title
        } else if (movieItem.original_title?.isNotEmpty() != null) {
            holder.title.text = movieItem.original_title
        }

        if (movieItem.poster_path?.isNotEmpty() != null) {
            Picasso.get()
                .load(IMAGE_BASE_POSTER + movieItem.poster_path)
                .into(holder.image)
        } else if (movieItem.backdrop_path?.isNotEmpty() != null) {
            Picasso.get()
                .load(IMAGE_BASE_BACKDROP + movieItem.backdrop_path)
                .into(holder.image)
        }

        Log.i(
            "item load",
            "loaded " + movie.id,
            null
        )
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MovieViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(view, mListener)
    }
}