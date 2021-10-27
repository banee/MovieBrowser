package com.hermanek.moviebrowserdemo.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hermanek.moviebrowserdemo.R
import com.hermanek.moviebrowserdemo.databinding.FragmentMoviesBinding
import com.hermanek.moviebrowserdemo.model.Changes
import com.hermanek.moviebrowserdemo.model.Movie
import com.hermanek.moviebrowserdemo.repository.Repository
import com.hermanek.moviebrowserdemo.ui.adapters.MoviesAdapter
import retrofit2.Call
import retrofit2.Response

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    companion object {
        const val fragmentTag: String = "MovieFragment"
    }

    private lateinit var binding: FragmentMoviesBinding
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var recyclerAdapter: MoviesAdapter
    private lateinit var viewModel: MoviesViewModel
    private lateinit var communicator: Communicator


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val view = binding.root
        communicator = activity as Communicator

        initRecyclerView()

        val repository = Repository()
        val viewModelFactory = MoviesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MoviesViewModel::class.java)

        viewModel.changesResponse.observe(viewLifecycleOwner, { response ->
            response.enqueue(object : retrofit2.Callback<Changes> {
                override fun onResponse(call: Call<Changes>, response: Response<Changes>) {
                    if (response.isSuccessful) {
                        viewModel.listData.value = removeAdultMovies(response.body()?.movies)
                    } else {
                        Toast.makeText(
                            activity,
                            getText(R.string.error_communication_failure),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Changes>, t: Throwable) {
                    Toast.makeText(
                        activity,
                        getText(R.string.error_communication_failure),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        })

        viewModel.listData.observe(viewLifecycleOwner, { data ->
            recyclerAdapter.updateData(data)
        })

        ArrayAdapter.createFromResource(
            activity as Context,
            R.array.spinner_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerNumOfDays.adapter = adapter
        }

        binding.spinnerNumOfDays.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    viewModel.getAllChanges()
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        viewModel.getAllChanges()
                    } else {
                        viewModel.getChangesFromLastDays(position)
                    }

                }

            }

        return view
    }

    private fun removeAdultMovies(movies: List<Movie>?): List<Movie>? {
        return movies?.filter { e -> !e.adult }?.toList()
    }

    private fun initRecyclerView() {
        layoutManager = if (resources.getBoolean(R.bool.isTablet)) {
            GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL, false)
        } else {
            GridLayoutManager(context, 3)
        }
        binding.movieListRecyclerView.layoutManager = layoutManager
        recyclerAdapter = MoviesAdapter()
        recyclerAdapter.setOnItemClickListener(object : MoviesAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val itemOnPosition = recyclerAdapter.getItemOnPosition(position)
                openMovieDetail(itemOnPosition.id)
            }
        })
        binding.movieListRecyclerView.adapter = recyclerAdapter
    }

    fun openMovieDetail(movieId: Int) {
        communicator.openMovieDetail(movieId)
    }

}