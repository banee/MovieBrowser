package com.hermanek.moviebrowserdemo.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hermanek.moviebrowserdemo.R
import com.hermanek.moviebrowserdemo.databinding.FragmentMoviesBinding
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.ui.adapters.MoviesAdapter
import com.hermanek.moviebrowserdemo.ui.adapters.MoviesAdapter.OnItemClickListener
import com.hermanek.moviebrowserdemo.util.AppUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies), OnItemClickListener {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private var layoutManager: RecyclerView.LayoutManager? = null

    private val viewModel by viewModels<MoviesViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMoviesBinding.bind(view)

        val adapter = MoviesAdapter(this)
        binding.apply {
            movieListRecyclerView.hasFixedSize()
            layoutManager = if (resources.getBoolean(R.bool.isTablet)) {
                GridLayoutManager(context, 4, GridLayoutManager.HORIZONTAL, false)
            } else {
                GridLayoutManager(context, 2)
            }
            movieListRecyclerView.layoutManager = layoutManager
            movieListRecyclerView.adapter = adapter
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        // TODO put on onchange of some filter
        // viewModel.getMovies(createParams())
    }

    // TODO prepared for filters
    private fun createParams(): HashMap<String, Any>? {
        val params: HashMap<String, Any> = HashMap()
        params.put("language", AppUtils.getSupportedLanguage())
        params.put("sort_by", "popularity.desc")
        params.put("include_adult", false)
        params.put("year", 2021)
        params.put("vote_count.gte", 1000)
        return params
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openMovie(movie: Movie) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(movie)
        findNavController().navigate(action)
    }

    override fun onItemClick(movie: Movie) {
        when (movie.id) {
            -1 -> {
                Toast.makeText(
                    activity,
                    getText(R.string.error_invalid_movie_data),
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                openMovie(movie)
            }
        }
    }

}