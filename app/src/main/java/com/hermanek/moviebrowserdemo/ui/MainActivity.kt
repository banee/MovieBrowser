package com.hermanek.moviebrowserdemo.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hermanek.moviebrowserdemo.R
import com.hermanek.moviebrowserdemo.databinding.ActivityMainBinding
import com.hermanek.moviebrowserdemo.ui.fragments.Communicator
import com.hermanek.moviebrowserdemo.ui.fragments.MovieDetailFragment
import com.hermanek.moviebrowserdemo.ui.fragments.MoviesFragment

private lateinit var binding: ActivityMainBinding

private lateinit var moviesFragment: MoviesFragment

class MainActivity : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = if (resources.getBoolean(R.bool.isTablet)) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        moviesFragment = MoviesFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, moviesFragment, MoviesFragment.fragmentTag)
            .commit()
    }

    override fun openMovieDetail(movieId: Int) {
        val bundle = Bundle()
        bundle.putInt("movieId", movieId)

        val movieDetailFragment = MovieDetailFragment()
        movieDetailFragment.arguments = bundle
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, movieDetailFragment, MovieDetailFragment.fragmentTag)
            .commit()
    }

    override fun onBackPressed() {
        val movieDetailFragment =
            supportFragmentManager.findFragmentByTag(MovieDetailFragment.fragmentTag)
        if (movieDetailFragment?.isVisible != null) {
            this.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    moviesFragment,
                    MoviesFragment.fragmentTag
                )
                .commit()
        } else {
            super.onBackPressed()
        }
    }
}