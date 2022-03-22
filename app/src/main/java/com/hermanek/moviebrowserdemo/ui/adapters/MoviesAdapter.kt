package com.hermanek.moviebrowserdemo.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hermanek.moviebrowserdemo.databinding.MovieListItemBinding
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.util.Constants
import com.squareup.picasso.Picasso


/**
 * Created by jhermanek on 28.02.2022.
 */

class MoviesAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class MovieViewHolder constructor(
        private val binding: MovieListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(movieItem: Movie) {
            binding.apply {
                when {
                    movieItem.title?.isNotEmpty() != null -> {
                        movieTitle.text = movieItem.title
                    }
                    movieItem.original_title?.isNotEmpty() != null -> {
                        movieTitle.text = movieItem.original_title
                    }
                    else -> {
                        movieTitle.text = movieItem.id.toString()
                    }
                }

                if (movieItem.poster_path?.isNotEmpty() != null) {
                    Picasso.get()
                        .load(Constants.IMAGE_BASE_POSTER + Constants.IMAGE_BASE_POSTER_small + movieItem.poster_path)
                        .into(movieImage)
                } else if (movieItem.backdrop_path?.isNotEmpty() != null) {
                    Picasso.get()
                        .load(Constants.IMAGE_BASE_BACKDROP + Constants.IMAGE_BASE_BACKDROP_small + movieItem.backdrop_path)
                        .into(movieImage)
                }
                Log.i(
                    "item load",
                    "loaded " + movieItem.id,
                    null
                )
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

        }
    }
}