package com.hermanek.moviebrowserdemo.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


/**
 * Created by jhermanek on 02.03.2022.
 */

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: Movie)

    @Query("SELECT * FROM movie_table WHERE id=:movieId AND translation=:language")
    suspend fun readMovie(movieId: Int, language: String): Movie

}