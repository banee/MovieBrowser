package com.hermanek.moviebrowserdemo.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


/**
 * Created by jhermanek on 02.03.2022.
 */

@Database(entities = [Movie::class], version = 4, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}