package com.hermanek.moviebrowserdemo.model

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by jhermanek on 02.03.2022.
 */

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.movieDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertMovieAndCheckPresence() = runTest {
        val movieItem = Movie(
            id = 1,
            adult = false,
            budget = 100,
            original_language = "english",
            vote_count = 10000,
            translation = "cs-CZ"
        )
        dao.addMovie(movieItem)
        val readMovie = dao.readMovie(movieId = movieItem.id, language = movieItem.translation!!)
        Truth.assertThat(movieItem).isEqualTo(readMovie)
    }

    @Test
    fun tryToLoadMovieFromEmptyDatabase() = runTest {
        val readMovie = dao.readMovie(1, language = "cs-CZ")
        Truth.assertThat(readMovie).isEqualTo(null)
    }

    @Test
    fun insertMovieDetailItemCheckWithDifferentLanguage() = runTest {
        val movieItem = Movie(
            id = 1,
            adult = false,
            budget = 100,
            original_language = "english",
            vote_count = 10000,
            translation = "cs-CZ"
        )
        dao.addMovie(movieItem)
        val readMovie = dao.readMovie(movieId = movieItem.id, language = "en-US")
        Truth.assertThat(readMovie).isEqualTo(null)
    }


}