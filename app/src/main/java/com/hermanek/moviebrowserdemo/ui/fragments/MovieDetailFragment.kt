package com.hermanek.moviebrowserdemo.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hermanek.moviebrowserdemo.R
import com.hermanek.moviebrowserdemo.databinding.FragmentMovieDetailBinding
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.util.AppUtils
import com.hermanek.moviebrowserdemo.util.Constants
import com.hermanek.moviebrowserdemo.util.SupportedLanguages
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Response
import java.util.stream.Collectors

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail), SupportedLanguages {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MovieDetailViewModel>()
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieDetailBinding.bind(view)
        val movie: Movie = args.movie

        viewModel.getMovieDetailFromDatabase(movie.id, AppUtils.getSupportedLanguage())
        viewModel.cachedMovie.observe(viewLifecycleOwner) { cachedMovie ->
            if (cachedMovie != null) {
                viewModel.changeMovie(cachedMovie)
            } else {
                downloadMovieDetails(movie)
            }
        }

        viewModel.movie.observe(viewLifecycleOwner) {
            populateLayout(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun downloadMovieDetails(movieItem: Movie?) {
        viewModel.getMovieDetail(movieItem?.id ?: -1, AppUtils.getSupportedLanguage())
        viewModel.detailResponse.observe(viewLifecycleOwner) { response ->
            response.enqueue(object : retrofit2.Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful) {
                        val movie = response.body() as Movie
                        movie.translation = AppUtils.getSupportedLanguage()
                        viewModel.changeMovie(movie)
                        viewModel.addMovieDetail(movie)
                    } else {
                        Toast.makeText(
                            activity,
                            getText(R.string.error_communication_failure),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Toast.makeText(
                        activity,
                        getText(R.string.error_communication_failure),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    private fun populateLayout(movie: Movie) {
        if (movie.poster_path?.isNotEmpty() != null) {
            Picasso.get()
                .load(Constants.IMAGE_BASE_POSTER + Constants.IMAGE_BASE_POSTER_large + movie.poster_path)
                .into(binding.cover)
        } else if (movie.backdrop_path?.isNotEmpty() != null) {
            Picasso.get()
                .load(Constants.IMAGE_BASE_BACKDROP + Constants.IMAGE_BASE_BACKDROP_large + movie.backdrop_path)
                .into(binding.cover)
        }
        if (!movie.title.isNullOrEmpty()) {
            binding.title.text = movie.title
        }
        if (!movie.spoken_languages.isNullOrEmpty()) {
            binding.language.text = movie.spoken_languages.stream().map { e -> e.english_name }
                ?.collect(Collectors.joining(", "))
        }
        if (!movie.genres.isNullOrEmpty()) {
            binding.genre.text =
                movie.genres.stream().map { e -> e.name }?.collect(Collectors.joining(", "))
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