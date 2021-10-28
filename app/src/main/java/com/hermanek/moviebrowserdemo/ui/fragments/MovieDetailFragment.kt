package com.hermanek.moviebrowserdemo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hermanek.moviebrowserdemo.R
import com.hermanek.moviebrowserdemo.databinding.FragmentMovieDetailBinding
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.repository.Repository
import com.hermanek.moviebrowserdemo.util.AppUtils
import com.hermanek.moviebrowserdemo.util.Constants
import com.squareup.picasso.Picasso
import java.util.stream.Collectors

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    companion object {
        const val fragmentTag: String = "MovieDetailFragment"
    }

    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        val movieId: Int? = arguments?.getInt("movieId")

        val repository = Repository()
        val viewModelFactory = MoviesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailViewModel::class.java)


        viewModel.getMovieDetail(movieId ?: -1)
        viewModel.movieDetail.observe(viewLifecycleOwner, { movieDetail ->
            populateLayout(movieDetail!!)
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, { error ->
            Toast.makeText(
                activity,
                getText(R.string.error_communication_failure),
                Toast.LENGTH_LONG
            ).show()
            Log.e(
                "communication error",
                "problem occurred while movies download",
                error
            )
        })

        return view
    }

    private fun populateLayout(movie: Movie) {
        if (movie.poster_path?.isNotEmpty() != null) {
            Picasso.get()
                .load(Constants.IMAGE_BASE_POSTER + movie.poster_path)
                .into(binding.cover)
        } else if (movie.backdrop_path?.isNotEmpty() != null) {
            Picasso.get()
                .load(Constants.IMAGE_BASE_BACKDROP + movie.backdrop_path)
                .into(binding.cover)
        }
        if (!movie.title.isNullOrEmpty()) {
            binding.title.text = movie.title
        }
        if (!movie.spoken_languages.isNullOrEmpty()) {
            binding.language.text = movie.spoken_languages.stream().map { e -> e.english_name }
                ?.collect(Collectors.joining(","))
        }
        if (!movie.genres.isNullOrEmpty()) {
            binding.genre.text =
                movie.genres.stream().map { e -> e.name }?.collect(Collectors.joining(","))
        }
        if (!movie.overview.isNullOrEmpty()) {
            binding.overview.text = movie.overview
        }
        if (!movie.release_date.isNullOrEmpty()) {
            binding.releaseDate.text =
                AppUtils.convertToFormat(movie.release_date, "dd.MM.yyyy")
        }
    }
}