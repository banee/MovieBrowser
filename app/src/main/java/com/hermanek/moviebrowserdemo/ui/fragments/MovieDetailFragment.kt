package com.hermanek.moviebrowserdemo.ui.fragments

import android.os.Bundle
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
import retrofit2.Call
import retrofit2.Response
import java.util.stream.Collectors

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    companion object {
        const val fragmentTag: String = "MovieDetailFragment"
    }

    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var viewModel: MovieDetailViewModel
    private var movieId: Int? = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        movieId = arguments?.getInt("movieId")


        val repository = Repository()
        val viewModelFactory = MoviesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailViewModel::class.java)


        viewModel.getMovieDetail(movieId ?: -1)
        viewModel.detailResponse.observe(viewLifecycleOwner, { response ->
            response.enqueue(object : retrofit2.Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful) {
                        val movie: Movie = response.body() as Movie
                        populateLayout(movie)
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