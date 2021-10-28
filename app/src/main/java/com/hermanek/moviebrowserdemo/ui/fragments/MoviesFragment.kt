package com.hermanek.moviebrowserdemo.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.hermanek.moviebrowserdemo.repository.Repository
import com.hermanek.moviebrowserdemo.ui.adapters.MoviesAdapter

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    companion object {
        const val fragmentTag: String = "MovieFragment"
    }

    private lateinit var binding: FragmentMoviesBinding
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var recyclerAdapter: MoviesAdapter
    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val view = binding.root

        initRecyclerView()

        val repository = Repository()
        val viewModelFactory = MoviesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MoviesViewModel::class.java)

        viewModel.movies.observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                recyclerAdapter.updateData(movies)
            }
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
        (activity as Communicator).openMovieDetail(movieId)
    }

}